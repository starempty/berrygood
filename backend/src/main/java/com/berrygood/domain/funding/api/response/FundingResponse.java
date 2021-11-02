package com.berrygood.domain.funding.api.response;

import com.berrygood.domain.funding.entity.Funding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingResponse {
    Funding funding;
    List<String> imgPath;
    int totalCnt;
    Long totalAmount;
}
