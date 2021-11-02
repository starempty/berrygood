package com.berrygood.domain.volunteer.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.berrygood.domain.volunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.berrygood.domain.user.entity.User;
import com.berrygood.domain.volunteer.entity.VolunteerParticipant;

public interface VolunteerParticipantRepository extends JpaRepository<VolunteerParticipant, Long> {
	// 유저로 참여 이력 조회
	Optional<List<VolunteerParticipant>> findAllByUser(User user);

	Optional<List<VolunteerParticipant>> findAllByVolunteer(Volunteer volunteer);

	// 유저와 봉사로 참가자 찾기
	Optional<VolunteerParticipant> findByUserAndVolunteer(User user, Volunteer volunteer);

	@Transactional
	void deleteAllByVolunteer(Volunteer volunteer);
}
