package com.berrygood.domain.donate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.global.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

import java.util.Date;

@Entity @Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Donate extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "COMP_NO")
    private Comp comp;
	@Column(nullable = false)
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date due;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date time;
    @Column
    private int amount;
}