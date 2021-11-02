package com.berrygood.domain.donate.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.berrygood.domain.user.entity.User;
import com.berrygood.global.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonateParticipant extends BaseEntity{
	@ManyToOne
	@JoinColumn(name = "DONATE_NO")
	Donate donate;
	
	@ManyToOne
	@JoinColumn(name = "USER_NO")
	User user;

	Long amount;
	
	String message;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	Date joinDate;
}
