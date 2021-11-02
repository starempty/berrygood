package com.berrygood.domain.volunteer.entity;

import com.berrygood.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerImg extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "volunteer_fk")
    private Volunteer volunteer;
    private String imgPath;
}
