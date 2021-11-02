package com.berrygood.domain.donate.application;

import com.berrygood.domain.donate.entity.Donate;
import com.berrygood.domain.donate.entity.DonateParticipant;
import com.berrygood.domain.user.entity.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DonateParticipantService {
    @Transactional
    void registerDonateParticipant(DonateParticipant donateParticipant);

    @Transactional
    void deleteDonateParticipant(DonateParticipant donateParticipant);

    @Transactional
    void deleteAllDonateParticipant(Donate donate);

    Optional<DonateParticipant> getDonateParticipant(User user, Donate donate);

    Optional<List<DonateParticipant>> getDonateParticipant(Donate donate);
    
    Optional<List<DonateParticipant>> getDonateParticipantByUser(User user);
}
