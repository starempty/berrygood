package com.berrygood.domain.blood.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.berrygood.domain.blood.api.request.BloodInfo;
import com.berrygood.domain.user.application.UserService;
import com.berrygood.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.berrygood.domain.blood.application.BloodService;
import com.berrygood.domain.blood.entity.Blood;

import io.swagger.annotations.*;

@Api(value="blood api", tags = {"Blood"})
@RestController
@RequestMapping("api/blood")
public class BloodApi { 
	@Autowired
	BloodService bloodService;

	@Autowired
	UserService userService;
	
	@PostMapping
	@ApiOperation(value="헌혈증 등록")
	@ApiResponses({
		@ApiResponse(code=200, message="성공"),
		@ApiResponse(code=500, message="서버 오류"),
	})
	public ResponseEntity<String> register(
			@RequestBody @ApiParam(value="등록할 헌혈증 정보", required = true) BloodInfo bloodInfo){
		if(userService.getUserByEmail(bloodInfo.getEmail()).isPresent()) {
			User user = userService.getUserByEmail(bloodInfo.getEmail()).get();
			Blood blood = Blood.builder()
					.user(user)
					.num(bloodInfo.getNum())
					.kind(bloodInfo.getKind())
					.date(bloodInfo.getDate())
					.build();
			bloodService.registerBlood(blood);
		}
		return ResponseEntity.status(200).body("Success");
	}

	@GetMapping("/{email}")
	@ApiOperation(value="헌혈증 조회")
	public ResponseEntity<List<Blood>> getBlood(@PathVariable String email){
		List<Blood> bloods;
		if(userService.getUserByEmail(email).isPresent()) {
			User user = userService.getUserByEmail(email).get();
			if(bloodService.getAllBlood(user).isPresent()) {
				bloods = bloodService.getAllBlood(user).get();
				return ResponseEntity.status(200).body(bloods);
			}
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
}
