package com.berrygood.domain.volunteer.application;

import com.berrygood.domain.volunteer.entity.Volunteer;
import com.berrygood.domain.volunteer.entity.VolunteerImg;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface VolunteerImgService {
    @Transactional
    void registerVolunteerImg(VolunteerImg volunteerImg);

    @Transactional
    void deleteVolunteerImg(Volunteer volunteer);

    Optional<List<VolunteerImg>> selectVolunteerImg(Volunteer volunteer);
}
