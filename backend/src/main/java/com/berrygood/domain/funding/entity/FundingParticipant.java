package com.berrygood.domain.funding.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.berrygood.domain.user.entity.User;
import com.berrygood.global.common.BaseEntity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingParticipant extends BaseEntity{
	@OneToOne
	@JoinColumn(name = "FUNDING_NO")
	Funding funding;
	
	@ManyToOne
	@JoinColumn(name = "USER_NO")
	User user;

	Long amount;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date joinDate;
}
