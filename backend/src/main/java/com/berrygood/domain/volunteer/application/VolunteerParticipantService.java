package com.berrygood.domain.volunteer.application;

import com.berrygood.domain.user.entity.User;
import com.berrygood.domain.volunteer.entity.Volunteer;
import com.berrygood.domain.volunteer.entity.VolunteerParticipant;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface VolunteerParticipantService {
    @Transactional
    void registerVolunteerParticipant(VolunteerParticipant volunteerParticipant);

    @Transactional
    void deleteVolunteerParticipant(VolunteerParticipant volunteerParticipant);

    @Transactional
    void deleteAllVolunteerParticipant(Volunteer volunteer);

    Optional<VolunteerParticipant> getVolunteerParticipant(User user, Volunteer volunteer);

    Optional<List<VolunteerParticipant>> getVolunteerParticipant(Volunteer volunteer);
    
    Optional<List<VolunteerParticipant>> getVolunteerParticipantByUser(User user);
}
