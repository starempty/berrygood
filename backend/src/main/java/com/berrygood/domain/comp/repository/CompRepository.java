package com.berrygood.domain.comp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.berrygood.domain.comp.entity.Comp;

@Repository
public interface CompRepository extends JpaRepository<Comp, Long> {
	
	// 기업 삭제
	int deleteByEmail(String email);
	
	// 기업 조회
	Optional<Comp> findByEmail(String email);
	
	// 이메일 중복 체크
	Boolean existsByEmail(String email);
}
