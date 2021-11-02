package com.berrygood.domain.funding.entity;

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
public class FundingImg extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "funding_fk")
    private Funding funding;

    private String imgPath;
}
