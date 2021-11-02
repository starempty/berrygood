package com.berrygood.domain.badge.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.berrygood.domain.badge.type.BadgeType;
import com.berrygood.global.common.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Badge extends BaseEntity{
	@Enumerated(EnumType.STRING)
	BadgeType badgeType;
	
	String imgPath;
	
	String content;
}
