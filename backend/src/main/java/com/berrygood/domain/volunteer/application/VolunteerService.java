package com.berrygood.domain.volunteer.application;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.berrygood.domain.user.entity.User;
import com.berrygood.domain.volunteer.entity.Volunteer;

public interface VolunteerService {

	@Transactional
	void registerVolunteer(Volunteer volunteer);
	
	@Transactional
	int deleteVolunteer(Long no);
	
	@Transactional
	int updateVolunteer(Long no, Volunteer volunteer);

	Optional<List<Volunteer>> selectAllVolunteer();

	Optional<Volunteer> selectVolunteer(Long no);

	Optional<List<Volunteer>> searchUser(User user);
}
