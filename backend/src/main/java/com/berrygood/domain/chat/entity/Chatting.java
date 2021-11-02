package com.berrygood.domain.chat.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.berrygood.domain.volunteer.entity.Volunteer;
import com.berrygood.global.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Chatting extends BaseEntity{
	@OneToOne
	@JoinColumn(name = "volunteer_no")
	Volunteer volunteer;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	String due;
}
