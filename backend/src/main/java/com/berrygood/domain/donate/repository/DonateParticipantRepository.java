package com.berrygood.domain.donate.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.berrygood.domain.donate.entity.Donate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.berrygood.domain.donate.entity.DonateParticipant;
import com.berrygood.domain.user.entity.User;

public interface DonateParticipantRepository extends JpaRepository<DonateParticipant, Long> {
	// 유저로 참여 이력 조회
	Optional<List<DonateParticipant>> findAllByUser(User user);

	// 유저와 도네로 참가자 찾기
	Optional<DonateParticipant> findByUserAndDonate(User user, Donate donate);

	Optional<List<DonateParticipant>> findAllByDonate(Donate donate);

	@Transactional
	void deleteAllByDonate(Donate donate);
	
	Optional<List<DonateParticipant>> findAllByUserNo(Long no);
}
