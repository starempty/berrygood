package com.berrygood.domain.volunteer.entity;

import com.berrygood.domain.user.entity.User;
import com.berrygood.global.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Volunteer extends BaseEntity {
    String name;
    @Column(columnDefinition = "LONGTEXT")
    String content;
    @ManyToOne
    @JoinColumn(name = "user_no")
    User user;
    int num;
    Date date;
    String type;
    String time;
}
