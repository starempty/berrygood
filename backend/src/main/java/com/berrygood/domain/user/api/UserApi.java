package com.berrygood.domain.user.api;

import com.berrygood.domain.user.api.request.UserInfo;
import com.berrygood.domain.user.application.UserService;
import com.berrygood.domain.user.entity.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    @ApiOperation(value = "회원 가입", notes = "<strong>이메일과 패스워드</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> register(@RequestBody @ApiParam(value = "회원가입 정보", required = true) User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user);
        return ResponseEntity.status(201).body("Success");
    }

    @PostMapping("/edit")
    @ApiOperation(value = "유저 정보 수정", notes = "<strong>아이디</strong>를 통해 유저 정보 수정")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "사용자 없음"),
    })
    public ResponseEntity<?> update(@RequestBody @ApiParam(value="회원 수정정보", required = true) UserInfo userInfo) {
    	if(userService.getUserByEmail(userInfo.getEmail()).isPresent()) {
    		userService.updateUser(userInfo);
            return ResponseEntity.status(200).body("Success");
        }
       	return ResponseEntity.status(404).body("INTERNAL_SERVER_ERROR");
    }

    @DeleteMapping("/{email}")
    @ApiOperation(value = "유저 정보 삭제", notes = "<strong>아이디</strong>를 통해 유저 정보 삭제")
    public ResponseEntity<?> delete(@PathVariable String email) {
        try {
            int response = userService.deleteUser(email);
            return ResponseEntity.status(204).body("Success");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
        }
    }

    @GetMapping("/{email}")
    @ApiOperation(value = "유저 정보 조회", notes = "<strong>아이디</strong>를 통해 유저 정보 조회")
    public ResponseEntity<?> detail(@PathVariable String email) {
        if(userService.getUserByEmail(email).isPresent()) {
            return ResponseEntity.status(200).body(userService.getUserByEmail(email).get());
        }
        return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
    }

    @GetMapping("/existEmail/{email}")
    @ApiOperation(value = "아이디 중복 체크", notes = "아이디가 존재하는지에 대해 Boolean 정보를 응답하다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "중복된 ID"),
    })
    public ResponseEntity<?> existEmail(@PathVariable String email) {
        if(userService.existEmail(email)) {
            return ResponseEntity.status(409).body("이미 중복된 아이디가 있습니다.");
        }
        return ResponseEntity.status(200).body("사용 가능한 아이디입니다.");
    }

    @GetMapping("/existNickname/{nickname}")
    @ApiOperation(value = "닉네임 중복 체크", notes = "닉네임이 존재하는지에 대해 Boolean 정보를 응답하다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "중복된 닉네임"),
    })
    public ResponseEntity<?> existNickname(@PathVariable String nickname) {
        if(userService.existNickname(nickname)) {
            return ResponseEntity.status(409).body("이미 중복된 닉네임이 있습니다.");
        }
        return ResponseEntity.status(200).body("사용 가능한 닉네임입니다.");
    }
}
