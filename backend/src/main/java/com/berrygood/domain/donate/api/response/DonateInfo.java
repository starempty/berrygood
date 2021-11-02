package com.berrygood.domain.donate.api.response;

import com.berrygood.domain.donate.entity.Donate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonateInfo {
    Donate donate;
    List<String> imgPath;
    int totalCnt;
    Long totalAmount;
    List<String> messages;
}