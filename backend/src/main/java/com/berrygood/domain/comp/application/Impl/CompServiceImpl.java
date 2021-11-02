package com.berrygood.domain.comp.application.Impl;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.berrygood.domain.comp.api.request.CompInfo;
import com.berrygood.domain.comp.application.CompService;
import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.domain.comp.repository.CompRepository;

@Service
public class CompServiceImpl implements CompService{

	@Autowired
	private CompRepository compRepository;

	@Override
	public void registerComp(Comp comp) { compRepository.save(comp); }

	@Override
	public int deleteComp(String email) {
		return compRepository.deleteByEmail(email);
	}

	@Override
	public int updateComp(CompInfo compInfo) {
		Optional<Comp> optionalComp = compRepository.findByEmail(compInfo.getEmail());
		if(optionalComp.isPresent()) {
			Comp comp = optionalComp.get();
			
			if(StringUtils.isNotBlank(compInfo.getPhone())) {
				comp.setPhone(compInfo.getPhone());
            }
            if(StringUtils.isNotBlank(compInfo.getAddress())) {
            	comp.setAddress(compInfo.getAddress());
            }
            if(StringUtils.isNotBlank(compInfo.getName())) {
            	comp.setName(compInfo.getName());
            }
            
			compRepository.save(comp);
			return 1;
		}
		return 0;
	}

	@Override
	public Optional<Comp> getCompByEmail(String email) {
		return compRepository.findByEmail(email);
	}

	@Override
	public Boolean existEmail(String email) {
		return compRepository.existsByEmail(email);
	}

}
