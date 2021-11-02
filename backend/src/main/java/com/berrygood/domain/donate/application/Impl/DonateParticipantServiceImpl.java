package com.berrygood.domain.donate.application.Impl;

import com.berrygood.domain.donate.application.DonateParticipantService;
import com.berrygood.domain.donate.entity.Donate;
import com.berrygood.domain.donate.entity.DonateParticipant;
import com.berrygood.domain.donate.repository.DonateParticipantRepository;
import com.berrygood.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonateParticipantServiceImpl implements DonateParticipantService {
    @Autowired
    private DonateParticipantRepository donateParticipantRepository;

    @Override
    public void registerDonateParticipant(DonateParticipant donateParticipant) {
        donateParticipantRepository.save(donateParticipant);
    }

    @Override
    public void deleteDonateParticipant(DonateParticipant donateParticipant) {
        donateParticipantRepository.delete(donateParticipant);
    }

    @Override
    public void deleteAllDonateParticipant(Donate donate) {
        donateParticipantRepository.deleteAllByDonate(donate);
    }

    @Override
    public Optional<DonateParticipant> getDonateParticipant(User user, Donate donate) {
        return donateParticipantRepository.findByUserAndDonate(user, donate);
    }

    @Override
    public Optional<List<DonateParticipant>> getDonateParticipant(Donate donate) {
        return donateParticipantRepository.findAllByDonate(donate);
    }

	@Override
	public Optional<List<DonateParticipant>> getDonateParticipantByUser(User user) {
		return donateParticipantRepository.findAllByUser(user);
	}
}
