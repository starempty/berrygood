package com.berrygood.domain.blood.repository;

import java.util.List;
import java.util.Optional;

import com.berrygood.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.berrygood.domain.blood.entity.Blood;

public interface BloodRepository  extends JpaRepository<Blood, Long> {
	//Optional<Blood> findByUser(int userNo);
	Optional<List<Blood>> findAllByUser(User user);
}
