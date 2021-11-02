package com.berrygood.domain.badge.api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.berrygood.domain.badge.application.BadgeService;
import com.berrygood.domain.badge.entity.Badge;
import com.berrygood.domain.blood.entity.Blood;
import com.berrygood.domain.badge.type.BadgeType;
import com.berrygood.domain.blood.application.BloodService;
import com.berrygood.domain.user.application.UserService;
import com.berrygood.domain.user.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "뱃지 API", tags = {"Badge"})
@RestController
@RequestMapping("api/badge")
public class BadgeApi {
	@Autowired
	BadgeService badgeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BloodService bloodService;
	
	
	final static String basePath = "images" + File.separator + "badge" + File.separator;
	
	@PostMapping
	@ApiOperation(value = "뱃지 등록", notes = "<strong>뱃지 등록</strong>")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")

	})
	public ResponseEntity<String> register(@RequestBody @ApiParam(value = "뱃지 등록 정보", required = true) Badge badge) {
		badgeService.registerBadge(badge);
		return ResponseEntity.status(200).body("Success");
	}
	
	@DeleteMapping("/{no}")
	@ApiOperation(value = "뱃지 삭제", notes = "<strong>no</strong>로 뱃지 삭제")
	public ResponseEntity<?> delete(@PathVariable Long no) {
	    try {
	        int response = badgeService.deleteBadge(no);
	        return ResponseEntity.status(200).body("Success");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
	    }
	}
	
	@PostMapping("/tier/{userNo}")
	@ApiOperation(value = "뱃지 조회", notes = "<strong>userNo</strong>로 뱃지 조회")
	public ResponseEntity<ArrayList<Badge>> updateBadgeTier(@PathVariable("userNo")Long userNo) {
		
		HashMap<String,Long> DPN = badgeService.selectUserToDonateBadge(userNo);
		HashMap<String,Long> FPN = badgeService.selectUserToFundingBadge(userNo);
		HashMap<String,Long> VPN = badgeService.selectUserToVolunteerBadge(userNo);
		
		List<Blood> bloods = null;
		if(userService.getUserByNo(userNo).isPresent()) {
			User user = userService.getUserByNo(userNo).get();
			if(bloodService.getAllBlood(user).isPresent()) {
				bloods = bloodService.getAllBlood(user).get();
			}
		}
		
		Badge Register = badgeService.selectBadge(BadgeType.Register);
		Badge FirstDoante = badgeService.selectBadge(BadgeType.FirstDoante);
		Badge ThreeTimesDonate = badgeService.selectBadge(BadgeType.ThreeTimesDonate);
		Badge LuckySeven = badgeService.selectBadge(BadgeType.LuckySeven);
		Badge TwoTimesBloodDonate = badgeService.selectBadge(BadgeType.TwoTimesBloodDonate);
		Badge FirstVolunteer = badgeService.selectBadge(BadgeType.FirstVolunteer);
		Badge ThreeTimesVolunteer = badgeService.selectBadge(BadgeType.ThreeTimesVolunteer);
		ArrayList<Badge> badge = new ArrayList<Badge>();
		Long tier = DPN.get("size") + FPN.get("size");
		Long time = VPN.get("amount");
		Long bloodDonateCnt = (long)bloods.size();
		badge.add(Register);
		if(tier > 0) {
			badge.add(FirstDoante);
			if(tier > 2) {
				badge.add(ThreeTimesDonate);
				if(tier > 6) {
					badge.add(LuckySeven);
				}
			}
		}
		if(bloodDonateCnt > 1) {
			badge.add(TwoTimesBloodDonate);
		}
		if(time > 0) {
			badge.add(FirstVolunteer);
			if(time > 2) {
				badge.add(ThreeTimesVolunteer);
			}
		}
		return ResponseEntity.status(200).body(badge);
	}
	
}
