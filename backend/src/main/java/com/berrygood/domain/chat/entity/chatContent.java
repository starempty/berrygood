package com.berrygood.domain.chat.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.berrygood.domain.user.entity.User;
import com.berrygood.global.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@NoArgsConstructor
public class chatContent extends BaseEntity{
	@ManyToOne
	@JoinColumn(name = "chatting_no")
	Chatting chatting;
	
	@OneToOne
	@JoinColumn(name = "user_no")
	User user;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	String time;
	
	String content;
}
