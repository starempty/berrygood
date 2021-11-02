package com.berrygood.domain.donate.application.Impl;

import com.berrygood.domain.donate.application.DonateImgService;
import com.berrygood.domain.donate.entity.Donate;
import com.berrygood.domain.donate.entity.DonateImg;
import com.berrygood.domain.donate.repository.DonateImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonateImgServiceImpl implements DonateImgService {
    @Autowired
    DonateImgRepository donateImgRepository;

    @Override
    public void registerDonateImg(DonateImg donateImg) { donateImgRepository.save(donateImg); }

    @Override
    public void deleteDonateImg(Donate donate) { donateImgRepository.deleteAllByDonate(donate); }

    @Override
    public Optional<List<DonateImg>> selectDonateImg(Donate donate) {
        return donateImgRepository.findAllByDonate(donate);
    }
}
