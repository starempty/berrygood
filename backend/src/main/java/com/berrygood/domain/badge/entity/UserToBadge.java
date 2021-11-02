package com.berrygood.domain.badge.entity;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.berrygood.domain.user.entity.User;
import com.berrygood.global.common.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class UserToBadge extends BaseEntity{
	@ManyToOne
	@JoinColumn(name = "BADGE_NO")
	Badge badge;
	
	@ManyToOne
	@JoinColumn(name = "USER_NO")
	User user;
}
