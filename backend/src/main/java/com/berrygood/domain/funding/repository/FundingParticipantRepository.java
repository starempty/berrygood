package com.berrygood.domain.funding.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.berrygood.domain.funding.entity.Funding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.berrygood.domain.funding.entity.FundingParticipant;
import com.berrygood.domain.user.entity.User;

public interface FundingParticipantRepository extends JpaRepository<FundingParticipant, Long> {
	@Transactional
	void deleteAllByFunding(Funding funding);

	// 유저로 참여 이력 조회
	Optional<List<FundingParticipant>> findAllByUser(User user);

	Optional<List<FundingParticipant>> findAllByFunding(Funding funding);

	// 유저와 펀딩으로 참가자 찾기
	Optional<FundingParticipant> findByUserAndFunding(User user, Funding funding);
}
