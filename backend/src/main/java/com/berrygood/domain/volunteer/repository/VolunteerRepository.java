package com.berrygood.domain.volunteer.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.berrygood.domain.user.entity.User;
import com.berrygood.domain.volunteer.entity.Volunteer;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
	// 삭제
	@Transactional
	int deleteByNo(Long no);
	
	//번호로 상세 조회
	Optional<Volunteer> findByNo(Long no);
	
	//유저의 전체 봉사 조회
	Optional<List<Volunteer>> findAllByUser(User user);
}
