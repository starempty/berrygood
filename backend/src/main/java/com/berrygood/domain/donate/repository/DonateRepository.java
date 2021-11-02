package com.berrygood.domain.donate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.domain.donate.entity.Donate;

import javax.transaction.Transactional;

@Repository
public interface DonateRepository extends JpaRepository<Donate, Long> {
    @Transactional
    int deleteByNo(Long no);

    // 특정 기부 조회
    Optional<Donate> findByNo(Long no);

    // 해당 기업 기부 전체 조회
    Optional<List<Donate>> findByComp(Comp comp);

    // 기부타이틀 중복 체크
    Boolean existsByComp(Comp comp);
}