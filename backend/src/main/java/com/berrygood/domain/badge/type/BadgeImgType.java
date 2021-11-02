package com.berrygood.domain.badge.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BadgeImgType {
	Register("images/badge/register.png"),
	FirstDoante("images/badge/firstdonate.png"),
	ThreeTimesDonate("images/badge/threetimesdonate.png"),
	LuckySeven("images/badge/luckyseven.png"),
	TwoTimesBloodDonate("images/badge/twotimesblooddonate.png"),
	FirstVolunteer("images/badge/firstvolunteer.png"),
	ThreeTimesVolunteer("images/badge/threetimesvolunteer.png");
	
	BadgeImgType(String val) {
//		if (!this.name().equals(val))
//            throw new IllegalArgumentException("Incorrect use of GroupType");
	}

}
