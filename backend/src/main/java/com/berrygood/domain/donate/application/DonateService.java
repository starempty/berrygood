package com.berrygood.domain.donate.application;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.domain.donate.entity.Donate;
import com.berrygood.domain.donate.entity.DonateImg;


public interface DonateService {
    @Transactional
    void registerDonate(Donate donate);

    @Transactional
    int deleteDonate(Long no);

    @Transactional
    int updateDonate(Long no, Donate donate); //기부 수정(기부 no(pk))

    Optional<List<Donate>> selectAllDonate(); // 기부 목록

    Optional<Donate> selectDonate(Long no);

    Optional<List<Donate>> searchDonateByComp(Comp comp);// 이름으로 검색
}