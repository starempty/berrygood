package com.berrygood.domain.donate.application;

import com.berrygood.domain.donate.entity.Donate;
import com.berrygood.domain.donate.entity.DonateImg;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DonateImgService {
    @Transactional
    void registerDonateImg(DonateImg donateImg);

    @Transactional
    void deleteDonateImg(Donate donate);

    Optional<List<DonateImg>> selectDonateImg(Donate donate);
}
