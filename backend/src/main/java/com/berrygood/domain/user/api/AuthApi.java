package com.berrygood.domain.user.api;

import com.berrygood.domain.comp.application.CompService;
import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.domain.user.api.request.LoginInfo;
import com.berrygood.domain.user.api.response.LoginDetail;
import com.berrygood.domain.user.application.UserService;
import com.berrygood.domain.user.entity.User;
import com.berrygood.global.auth.BerryGoodUserDetails;
import com.berrygood.global.auth.JwtTokenUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@Api(value = "인증 API", tags = {"Auth"})
@RestController
@RequestMapping("/api/users/auth")
public class AuthApi {

    @Autowired
    UserService userService;

    @Autowired
    CompService compService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> login(
            @RequestBody @ApiParam(value="로그인 정보", required = true) LoginInfo loginInfo) {

        String email = loginInfo.getEmail();
        String pw = loginInfo.getPw();

        Optional<User> oUser = userService.getUserByEmail(email);
        if(oUser.isPresent() && passwordEncoder.matches(pw, oUser.get().getPassword())) {
            return ResponseEntity.status(200).body(JwtTokenUtil.getToken(email));
        }
        return ResponseEntity.status(401).body(null);
    }

    @GetMapping("/me")
    @ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> checkMe(@ApiIgnore Authentication authentication) {
        BerryGoodUserDetails user = (BerryGoodUserDetails) authentication.getDetails();
        return ResponseEntity.status(200).body(LoginDetail.of(user.getUsername(), "user"));
    }

    @PostMapping("/comp/login")
    @ApiOperation(value = "기업로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> compLogin(
            @RequestBody @ApiParam(value="로그인 정보", required = true) LoginInfo loginInfo) {

        String email = loginInfo.getEmail();
        String pw = loginInfo.getPw();

        Optional<Comp> cUser = compService.getCompByEmail(email);
        if(cUser.isPresent() && passwordEncoder.matches(pw, cUser.get().getPassword())) {
            return ResponseEntity.status(200).body(JwtTokenUtil.getToken(email));
        }
        return ResponseEntity.status(401).body(null);
    }

    @GetMapping("/comp/me")
    @ApiOperation(value = "기업 회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> compCheckMe(@ApiIgnore Authentication authentication) {
        BerryGoodUserDetails user = (BerryGoodUserDetails) authentication.getDetails();
        return ResponseEntity.status(200).body(LoginDetail.of(user.getUsername(), "company"));
    }
}
