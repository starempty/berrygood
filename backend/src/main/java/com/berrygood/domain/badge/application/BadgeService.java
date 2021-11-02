package com.berrygood.domain.badge.application;

import java.util.HashMap;

import javax.transaction.Transactional;

import com.berrygood.domain.badge.entity.Badge;
import com.berrygood.domain.badge.entity.UserToBadge;
import com.berrygood.domain.badge.type.BadgeType;

public interface BadgeService {
	/* ------------ 배지 -------------*/
	@Transactional
	void registerBadge(Badge badge); // 배지 등록 (admin)
	
	@Transactional
	int deleteBadge(Long no); // 배지 삭제 (admin)
	
	@Transactional
	Badge selectBadge(BadgeType type);
	
	
	/* ------------ 유저 to 배지 -------------*/
	
	@Transactional
	HashMap<String, Long> selectUserToDonateBadge(Long no); // 기부 배지 티어 확인

	@Transactional
	HashMap<String, Long> selectUserToVolunteerBadge(Long no); // 봉사 배지 티어 확인

	@Transactional
	HashMap<String, Long> selectUserToFundingBadge(Long no); // 펀딩 배지 티어 확인
	
	@Transactional
	int updateUserToDonateBadge(Long no, UserToBadge badge); // 배지 업데이트

	@Transactional
	int updateUserToVolunteerBadge(Long no, UserToBadge badge); // 배지 업데이트

	@Transactional
	int updateUserToFundingBadge(Long no, UserToBadge badge); // 배지 업데이트
	
}

