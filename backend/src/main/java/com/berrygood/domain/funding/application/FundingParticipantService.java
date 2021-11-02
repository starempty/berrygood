package com.berrygood.domain.funding.application;

import com.berrygood.domain.funding.entity.Funding;
import com.berrygood.domain.funding.entity.FundingParticipant;
import com.berrygood.domain.user.entity.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface FundingParticipantService {
    @Transactional
    void registerFundingParticipant(FundingParticipant fundingParticipant);

    @Transactional
    void deleteAllFundingParticipant(Funding funding);

    @Transactional
    void deleteFundingParticipant(FundingParticipant fundingParticipant);

    Optional<FundingParticipant> getFundingParticipant(User user, Funding funding);

    Optional<List<FundingParticipant>> getFundingParticipant(Funding funding);
    
    Optional<List<FundingParticipant>> getFundingParticipantByUser(User user);
}
