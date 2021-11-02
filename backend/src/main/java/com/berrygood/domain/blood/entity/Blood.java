package com.berrygood.domain.blood.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.berrygood.global.common.BaseEntity;
import com.berrygood.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Entity
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Blood extends BaseEntity{
	@ManyToOne
	@JoinColumn(name = "USER_NO")
	private User user;
	@Column(nullable = false)
	private String num;
	@Column(nullable = false)
	private String kind;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date date;
}
