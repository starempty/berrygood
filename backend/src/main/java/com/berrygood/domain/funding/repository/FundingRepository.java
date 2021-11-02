package com.berrygood.domain.funding.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.domain.funding.entity.Funding;

public interface FundingRepository extends JpaRepository<Funding, Long> {
	// 삭제
	@Transactional
	int deleteByNo(Long no);

	// 조회
	Optional<Funding> findByCompNo(Long compNo);

	// 상세 조회 게시글 제목으로
	Optional<Funding> findByName(String name);

	// 번호로 조회
	Optional<Funding> findByNo(Long no);

	// 기업의 펀딩 전체 조회
	Optional<List<Funding>> findAllByComp(Comp comp);

	// 리스트 전체 조회
	List<Funding> findAll();
}
