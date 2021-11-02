package com.berrygood.domain.donate.application.Impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.berrygood.domain.donate.entity.DonateImg;
import com.berrygood.domain.donate.repository.DonateImgRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.domain.donate.application.DonateService;
import com.berrygood.domain.donate.entity.Donate;
import com.berrygood.domain.donate.repository.DonateRepository;


@Service
public class DonateServiceImpl implements DonateService{

    @Autowired
    DonateRepository donateRepository;

    @Override
    public void registerDonate(Donate donate) {
        donateRepository.save(donate);
    }

    @Override
    public int deleteDonate(Long no) { return donateRepository.deleteByNo(no); }

    @Override
    public int updateDonate(Long no, Donate donateInfo) {
        Optional<Donate> optionalDonate = donateRepository.findByNo(no);
        if(optionalDonate.isPresent()) {
            Donate donate = optionalDonate.get();
            if(Objects.nonNull(donateInfo.getDue())) {
                donate.setDue(donateInfo.getDue());
            }
            if(StringUtils.isNotBlank(donateInfo.getContent())) {
                donate.setContent(donateInfo.getContent());
            }
            if(StringUtils.isNotBlank(donateInfo.getName())) {
                donate.setName(donateInfo.getName());
            }
            donateRepository.save(donate);
            return 1;
        }
        return 0;
    }

    @Override
    public Optional<List<Donate>> selectAllDonate() {
        return Optional.of(donateRepository.findAll());
    }

    @Override
    public Optional<Donate> selectDonate(Long no) { return donateRepository.findByNo(no); }

    @Override
    public Optional<List<Donate>> searchDonateByComp(Comp comp) {
        return donateRepository.findByComp(comp);
    }

}
