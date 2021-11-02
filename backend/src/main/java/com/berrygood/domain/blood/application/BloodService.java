package com.berrygood.domain.blood.application;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.berrygood.domain.blood.entity.Blood;
import com.berrygood.domain.user.entity.User;

public interface BloodService {
	@Transactional
	void registerBlood(Blood blood);

	Optional<Blood> getBlood(int user);

	Optional<List<Blood>> getAllBlood(User user);
}
