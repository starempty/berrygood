package com.berrygood.domain.funding.application.Impl;

import com.berrygood.domain.funding.application.FundingParticipantService;
import com.berrygood.domain.funding.entity.Funding;
import com.berrygood.domain.funding.entity.FundingParticipant;
import com.berrygood.domain.funding.repository.FundingParticipantRepository;
import com.berrygood.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FundingParticipantServiceImpl implements FundingParticipantService {
    @Autowired
    private FundingParticipantRepository fundingParticipantRepository;

    @Override
    public void registerFundingParticipant(FundingParticipant fundingParticipant) {
        fundingParticipantRepository.save(fundingParticipant);
    }

    @Override
    public void deleteAllFundingParticipant(Funding funding) {
        fundingParticipantRepository.deleteAllByFunding(funding);
    }

    @Override
    public void deleteFundingParticipant(FundingParticipant fundingParticipant) {
        fundingParticipantRepository.delete(fundingParticipant);
    }

    @Override
    public Optional<FundingParticipant> getFundingParticipant(User user, Funding funding) {
        return fundingParticipantRepository.findByUserAndFunding(user, funding);
    }

    @Override
    public Optional<List<FundingParticipant>> getFundingParticipant(Funding funding) {
        return fundingParticipantRepository.findAllByFunding(funding);
    }

	@Override
	public Optional<List<FundingParticipant>> getFundingParticipantByUser(User user) {
		return fundingParticipantRepository.findAllByUser(user);
	}
}
