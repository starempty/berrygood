package com.berrygood.domain.user.entity;

import com.berrygood.global.common.BaseEntity;
import com.berrygood.global.common.BaseUserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseUserEntity {
    @Column(nullable = false)
    private char gender;
    private String address;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date birthday;
}
