package com.berrygood.domain.comp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.berrygood.domain.comp.api.request.CompInfo;
import com.berrygood.domain.comp.application.CompService;
import com.berrygood.domain.comp.entity.Comp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "기업 API", tags = {"Comp"})
@RestController
@RequestMapping("api/comp")
public class CompApi {
	
	@Autowired
	CompService compService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping
	@ApiOperation(value = "기업 회원 가입", notes = "<strong>email, password</strong>로 회원가입")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")

	})
	public ResponseEntity<String> register(@RequestBody @ApiParam(value = "회원가입 정보", required = true) Comp comp) {
		comp.setPassword(passwordEncoder.encode(comp.getPassword()));
		compService.registerComp(comp);
		return ResponseEntity.status(201).body("Success");
	}
	
	@PostMapping("/edit")
	@ApiOperation(value = "기업 정보 수정", notes = "<strong>기업 객체</strong>로 기업 정보 수정")
	public ResponseEntity<?> update(@RequestBody @ApiParam(value="기업 수정정보", required = true) CompInfo compInfo){
		if(compService.getCompByEmail(compInfo.getEmail()).isPresent()) {
			compService.updateComp(compInfo);
			return ResponseEntity.status(200).body("Success");
		}
		return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
	}
	
	@DeleteMapping("/{email}")
	@ApiOperation(value = "기업 정보 삭제", notes = "<strong>이메일</strong>을 통해 기업 정보 삭제")
	public ResponseEntity<?> delete(@PathVariable String email) {
	    try {
	        int response = compService.deleteComp(email);
	        return ResponseEntity.status(204).body("Success");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
	    }
	}

	@GetMapping("/{email}")
	@ApiOperation(value = "기업 정보 조회", notes = "<strong>아이디</strong>를 통해 기업 정보 조회")
	public ResponseEntity<?> compDetail(@PathVariable String email) {
		if(compService.getCompByEmail(email).isPresent()) {
			return ResponseEntity.status(200).body(compService.getCompByEmail(email).get());
		}
		return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
	}
	
	@GetMapping("/existEmail/{email}")
	@ApiOperation(value = "이메일 중복 체크", notes = "이메일이 존재하는지에 관한 boolean 반환")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 409, message = "중복된 ID")
	})
	public ResponseEntity<?> existEmail(@PathVariable String email) {
		if(compService.existEmail(email)) {
			return ResponseEntity.status(409).body("이미 중복된 이메일이 존재합니다");
		}
		return ResponseEntity.status(200).body("사용 가능한 이메일입니다");
	}
}
