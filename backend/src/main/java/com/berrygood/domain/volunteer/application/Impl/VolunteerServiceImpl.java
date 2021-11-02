package com.berrygood.domain.volunteer.application.Impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berrygood.domain.user.entity.User;
import com.berrygood.domain.volunteer.application.VolunteerService;
import com.berrygood.domain.volunteer.entity.Volunteer;
import com.berrygood.domain.volunteer.repository.VolunteerRepository;
@Service
public class VolunteerServiceImpl implements VolunteerService {

	@Autowired
	VolunteerRepository volunteerRepo;
	
	@Override
	public void registerVolunteer(Volunteer volunteer) {
		volunteerRepo.save(volunteer);
	}

	@Override
	public int deleteVolunteer(Long no) {
		return volunteerRepo.deleteByNo(no);
	}

	@Override
	public int updateVolunteer(Long no, Volunteer volunteerInfo) {
		Optional<Volunteer> optionalVolunteer = volunteerRepo.findByNo(no);
		if(optionalVolunteer.isPresent()) {
			Volunteer volunteer = optionalVolunteer.get();
			if(StringUtils.isNotBlank(volunteerInfo.getContent())) {
				volunteer.setContent(volunteerInfo.getContent());
			}
			if(Objects.nonNull(volunteerInfo.getDate())) {
				volunteer.setDate(volunteerInfo.getDate());
			}
			if(StringUtils.isNotBlank(volunteerInfo.getName())) {
				volunteer.setName(volunteerInfo.getName());
			}
			if(volunteerInfo.getNum() != 0 && volunteer.getNum() != volunteerInfo.getNum()) {
				volunteer.setNum(volunteerInfo.getNum());
			}
			if(StringUtils.isNotBlank(volunteerInfo.getTime())) {
				volunteer.setTime(volunteerInfo.getTime());
			}
			if(StringUtils.isNotBlank(volunteerInfo.getType())) {
				volunteer.setType(volunteerInfo.getType());
			}

			volunteerRepo.save(volunteer);
			return 1;
		}
		return 0;
	}

	@Override
	public Optional<List<Volunteer>> selectAllVolunteer() {
		return Optional.of(volunteerRepo.findAll());
	}

	@Override
	public Optional<Volunteer> selectVolunteer(Long no) {
		return volunteerRepo.findByNo(no);
	}

	@Override
	public Optional<List<Volunteer>> searchUser(User user) {
		return volunteerRepo.findAllByUser(user);
	}
}
