package com.berrygood.domain.badge.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BadgeType {
	Register, // images/badge/register.png / 회원 가입 감사
	FirstDoante, // images/badge/firstdonate.png / 첫 기부 기념
	ThreeTimesDonate, // images/badge/threetimesdonate.png / 기부 3회 달성
	LuckySeven, // images/badge/luckyseven.png / 기부 럭키 세븐
	TwoTimesBloodDonate, // images/badge/twotimesblooddonate.png / 헌혈증 등록 2회 달성
	FirstVolunteer, // images/badge/firstvolunteer.png / 첫 봉사 기념
	ThreeTimesVolunteer; // images/badge/threetimesvolunteer.png / 당신은 마더테레사
}
