package com.berrygood.domain.donate.entity;

import com.berrygood.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonateImg extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "donate_fk")
    private Donate donate;
    private String imgPath;
}