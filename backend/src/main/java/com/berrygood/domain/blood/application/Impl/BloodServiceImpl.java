package com.berrygood.domain.blood.application.Impl;

import java.util.List;
import java.util.Optional;

import com.berrygood.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berrygood.domain.blood.application.BloodService;
import com.berrygood.domain.blood.entity.Blood;
import com.berrygood.domain.blood.repository.BloodRepository;

@Service
public class BloodServiceImpl implements BloodService{

	@Autowired
	BloodRepository bloodRepo;
	
	@Override
	public void registerBlood(Blood blood) {
		bloodRepo.save(blood);
	}
	
	@Override
	public Optional<Blood> getBlood(int userNo){
		return Optional.empty();//bloodRepo.findByUser(userNo);
	}

	@Override
	public Optional<List<Blood>> getAllBlood(User user) {
		return bloodRepo.findAllByUser(user);
	}
}
