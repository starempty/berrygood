package com.berrygood.global.auth;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.berrygood.domain.comp.application.CompService;
import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.domain.user.application.UserService;
import com.berrygood.domain.user.entity.User;
import com.berrygood.global.util.ResponseBodyWriteUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 요청 헤더에 jwt 토큰이 있는 경우, 토큰 검증 및 인증 처리 로직 정의.
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private UserService userService;
    private CompService compService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   UserService userService, CompService compService) {
        super(authenticationManager);
        this.userService = userService;
        this.compService = compService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Read the Authorization header, where the JWT Token should be
        String header = request.getHeader(JwtTokenUtil.HEADER_STRING);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // If header is present, try grab user principal from database and perform authorization
            Authentication authentication = getAuthentication(request);
            // jwt 토큰으로 부터 획득한 인증 정보(authentication) 설정.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            ResponseBodyWriteUtil.sendError(request, response, ex);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Transactional(readOnly = true)
    public Authentication getAuthentication(HttpServletRequest request) throws Exception {
        String token = request.getHeader(JwtTokenUtil.HEADER_STRING);
        // 요청 헤더에 Authorization 키값에 jwt 토큰이 포함된 경우에만, 토큰 검증 및 인증 처리 로직 실행.
        if (token != null) {
            // parse the token and validate it (decode)
            JWTVerifier verifier = JwtTokenUtil.getVerifier();
            JwtTokenUtil.handleError(token);
            DecodedJWT decodedJWT = verifier.verify(token.replace(JwtTokenUtil.TOKEN_PREFIX, ""));
            String email = decodedJWT.getSubject();

            // Search in the DB if we find the user by token subject (username)
            // If so, then grab user details and create spring auth token using username, pass, authorities/roles
            if (email != null) {
                // jwt 토큰에 포함된 계정 정보(userId) 통해 실제 디비에 해당 정보의 계정이 있는지 조회.
                if(userService.getUserByEmail(email).isPresent()) {
                    User user = userService.getUserByEmail(email).get();
                    // 식별된 정상 유저인 경우, 요청 context 내에서 참조 가능한 인증 정보(jwtAuthentication) 생성.
                    BerryGoodUserDetails userDetails = new BerryGoodUserDetails(user);
                    UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(email,
                            null, userDetails.getAuthorities());
                    jwtAuthentication.setDetails(userDetails);
                    return jwtAuthentication;
                }
                // jwt 토큰에 포함된 계정 정보 통해 실제 디비에 기업 정보가 있는지
                else if(compService.getCompByEmail(email).isPresent()) {
                    Comp comp = compService.getCompByEmail(email).get();
                    BerryGoodUserDetails userDetails = new BerryGoodUserDetails(comp);
                    UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(email,
                            null, userDetails.getAuthorities());
                    jwtAuthentication.setDetails(userDetails);
                    return jwtAuthentication;
                }
            }
            return null;
        }
        return null;
    }
}