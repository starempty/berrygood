package com.berrygood.domain.funding.application.Impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.domain.funding.application.FundingService;
import com.berrygood.domain.funding.entity.Funding;
import com.berrygood.domain.funding.repository.FundingRepository;
@Service
public class FundingServiceImpl implements FundingService {
	@Autowired
	FundingRepository fundingRepo;
	
	@Override
	public void registerFunding(Funding funding) {
		fundingRepo.save(funding);
	}

	@Override
	public int deleteFunding(Long no) {
		return fundingRepo.deleteByNo(no);
	}

	@Override
	public int updateFunding(Long no, Funding fundingInfo) {
		Optional<Funding> optionalFunding = fundingRepo.findByNo(no);
		if(optionalFunding.isPresent()) {
			Funding funding = optionalFunding.get();
			if(fundingInfo.getAmount() != 0 && fundingInfo.getAmount() != funding.getAmount()) {
				funding.setAmount(fundingInfo.getAmount());
			}
			if(StringUtils.isNotBlank(fundingInfo.getContent())) {
				funding.setContent(fundingInfo.getContent());
			}
			if(Objects.nonNull(funding.getDue())) {
				funding.setDue(fundingInfo.getDue());
			}
			if(StringUtils.isNotBlank(fundingInfo.getName())) {
				funding.setName(fundingInfo.getName());
			}
			fundingRepo.save(funding);
			return 1;
		}
		return 0;
	}

	@Override
	public Optional<List<Funding>> selectAllFunding() {
		return Optional.of(fundingRepo.findAll());
	}

	@Override
	public Optional<Funding> selectFunding(Long no) { return fundingRepo.findByNo(no); }

	@Override
	public Optional<List<Funding>> searchFunding(Comp comp) {
		return fundingRepo.findAllByComp(comp);
	}

}
