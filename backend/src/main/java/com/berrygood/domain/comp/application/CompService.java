package com.berrygood.domain.comp.application;

import java.util.Optional;

import javax.transaction.Transactional;

import com.berrygood.domain.comp.api.request.CompInfo;
import com.berrygood.domain.comp.entity.Comp;

public interface CompService {
	@Transactional
	void registerComp(Comp comp);
	
	@Transactional
	int deleteComp(String email);
	
	@Transactional
	int updateComp(CompInfo comp);
	
	Optional<Comp> getCompByEmail(String email);
	
	Boolean existEmail(String email);
}
