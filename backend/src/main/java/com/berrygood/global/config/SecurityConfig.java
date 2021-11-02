package com.berrygood.global.config;

import com.berrygood.domain.comp.application.CompService;
import com.berrygood.domain.user.application.UserService;
import com.berrygood.global.auth.BerryGoodUserDetailService;
import com.berrygood.global.auth.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BerryGoodUserDetailService berryGoodUserDetailService;

    private final UserService userService;

    private final CompService compService;

    public SecurityConfig(BerryGoodUserDetailService berryGoodUserDetailService,
                          UserService userService, CompService compService) {
        this.berryGoodUserDetailService = berryGoodUserDetailService;
        this.userService = userService;
        this.compService = compService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // DAO 기반으로 Authentication Provider를 생성
    // BCrypt Password Encoder와 UserDetailService 구현체를 설정
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.berryGoodUserDetailService);
        return daoAuthenticationProvider;
    }

    // DAO 기반의 Authentication Provider가 적용되도록 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 하지않음
                .and().addFilter(new JwtAuthenticationFilter(authenticationManager(), userService, compService)) // HTTP 요청에 JWT 토큰
                                                                                                    // 인증 필터를 거치도록 필터를
                                                                                                    // 추가
                .authorizeRequests().antMatchers("/api/**").permitAll()
                // .antMatchers("/api/user/login").permitAll()
                // .antMatchers(HttpMethod.POST, "/api/user").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .anyRequest().authenticated().and().cors();
        /*
         * http .httpBasic().disable() .csrf().disable()
         * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         * // 토큰 기반 인증이므로 세션 사용 하지않음 .and() //.addFilter(new
         * JwtAuthenticationFilter(authenticationManager(), userService)) //HTTP 요청에 JWT
         * 토큰 인증 필터를 거치도록 필터를 추가 .authorizeRequests() .antMatchers("/api/**")
         * .permitAll() //.antMatchers("/api/v1/users/me").authenticated() //인증이 필요한
         * URL과 필요하지 않은 URL에 대하여 설정 //.anyRequest().permitAll() .and().cors();
         */
    }
}
