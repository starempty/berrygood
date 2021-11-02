package com.berrygood.domain.blood.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class BloodInfo {
    private String email;
    private String num;
    private String kind;
    private Date date;
}
