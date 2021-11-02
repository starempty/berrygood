package com.berrygood.domain.funding.repository;

import com.berrygood.domain.funding.entity.Funding;
import com.berrygood.domain.funding.entity.FundingImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface FundingImgRepository extends JpaRepository<FundingImg, Long> {
    @Transactional
    void deleteAllByFunding(Funding funding);

    // 특정 펀딩에 해당하는 이미지 전체 반환
    Optional<List<FundingImg>> findAllByFunding(Funding funding);
}