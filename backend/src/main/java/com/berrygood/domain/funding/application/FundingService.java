package com.berrygood.domain.funding.application;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.domain.funding.entity.Funding;

public interface FundingService {
	@Transactional
	void registerFunding(Funding funding);

	@Transactional
	int deleteFunding(Long no);

	@Transactional
	int updateFunding(Long no, Funding funding); // 펀딩 수정

	Optional<List<Funding>> selectAllFunding(); // 조회

	Optional<Funding> selectFunding(Long no); // 상세 조회

	Optional<List<Funding>> searchFunding(Comp comp); // 이름으로 검색
	
}
