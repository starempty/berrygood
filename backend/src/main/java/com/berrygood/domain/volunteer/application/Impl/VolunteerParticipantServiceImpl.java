package com.berrygood.domain.volunteer.application.Impl;

import com.berrygood.domain.user.entity.User;
import com.berrygood.domain.volunteer.application.VolunteerParticipantService;
import com.berrygood.domain.volunteer.entity.Volunteer;
import com.berrygood.domain.volunteer.entity.VolunteerParticipant;
import com.berrygood.domain.volunteer.repository.VolunteerParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerParticipantServiceImpl implements VolunteerParticipantService {
    @Autowired
    private VolunteerParticipantRepository volunteerParticipantRepository;

    @Override
    public void registerVolunteerParticipant(VolunteerParticipant volunteerParticipant) {
        volunteerParticipantRepository.save(volunteerParticipant);
    }

    @Override
    public void deleteVolunteerParticipant(VolunteerParticipant volunteerParticipant) {
        volunteerParticipantRepository.delete(volunteerParticipant);
    }

    @Override
    public void deleteAllVolunteerParticipant(Volunteer volunteer) {
        volunteerParticipantRepository.deleteAllByVolunteer(volunteer);
    }

    @Override
    public Optional<VolunteerParticipant> getVolunteerParticipant(User user, Volunteer volunteer) {
        return volunteerParticipantRepository.findByUserAndVolunteer(user, volunteer);
    }

    @Override
    public Optional<List<VolunteerParticipant>> getVolunteerParticipant(Volunteer volunteer) {
        return volunteerParticipantRepository.findAllByVolunteer(volunteer);
    }

	@Override
	public Optional<List<VolunteerParticipant>> getVolunteerParticipantByUser(User user) {
		return volunteerParticipantRepository.findAllByUser(user);
	}
}
