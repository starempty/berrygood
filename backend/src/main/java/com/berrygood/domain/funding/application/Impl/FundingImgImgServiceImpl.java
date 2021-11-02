package com.berrygood.domain.funding.application.Impl;

import com.berrygood.domain.funding.application.FundingImgService;
import com.berrygood.domain.funding.entity.Funding;
import com.berrygood.domain.funding.entity.FundingImg;
import com.berrygood.domain.funding.repository.FundingImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FundingImgImgServiceImpl implements FundingImgService {
    @Autowired
    FundingImgRepository fundingImgRepository;

    @Override
    public void registerFundingImg(FundingImg fundingImg) { fundingImgRepository.save(fundingImg); }

    @Override
    public void deleteFundingImg(Funding funding) { fundingImgRepository.deleteAllByFunding(funding); }

    @Override
    public Optional<List<FundingImg>> selectFundingImg(Funding funding) {
        return fundingImgRepository.findAllByFunding(funding);
    }
}
