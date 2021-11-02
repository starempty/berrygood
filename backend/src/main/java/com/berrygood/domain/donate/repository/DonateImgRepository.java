package com.berrygood.domain.donate.repository;

import com.berrygood.domain.donate.entity.Donate;
import com.berrygood.domain.donate.entity.DonateImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonateImgRepository extends JpaRepository<DonateImg, Long> {
    // 특정 기부에 해당하는 이미지 전체 반환
    Optional<List<DonateImg>> findAllByDonate(Donate donate);

    //삭제
    @Transactional
    void deleteAllByDonate(Donate donate);
}