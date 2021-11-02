package com.berrygood.domain.funding.application;

import com.berrygood.domain.funding.entity.Funding;
import com.berrygood.domain.funding.entity.FundingImg;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface FundingImgService {
    @Transactional
    void registerFundingImg(FundingImg fundingImg);

    @Transactional
    void deleteFundingImg(Funding funding);

    Optional<List<FundingImg>> selectFundingImg(Funding funding);
}
