package com.berrygood.domain.funding.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.global.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Funding extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "COMP_NO")
    private Comp comp;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private int amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date due;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date time;
    private int calc;
}
