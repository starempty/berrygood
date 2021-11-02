package com.berrygood.domain.comp.entity;

import com.berrygood.global.common.BaseEntity;
import com.berrygood.global.common.BaseUserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Comp extends BaseUserEntity {
	private String address;
}
