package com.berrygood.domain.volunteer.application.Impl;

import com.berrygood.domain.volunteer.application.VolunteerImgService;
import com.berrygood.domain.volunteer.entity.Volunteer;
import com.berrygood.domain.volunteer.entity.VolunteerImg;
import com.berrygood.domain.volunteer.repository.VolunteerImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerImgServiceImpl implements VolunteerImgService {
    @Autowired
    VolunteerImgRepository volunteerImgRepository;

    @Override
    public void registerVolunteerImg(VolunteerImg volunteerImg) { volunteerImgRepository.save(volunteerImg); }

    @Override
    public void deleteVolunteerImg(Volunteer volunteer) {
        volunteerImgRepository.deleteAllByVolunteer(volunteer);
    }

    @Override
    public Optional<List<VolunteerImg>> selectVolunteerImg(Volunteer volunteer) {
        return volunteerImgRepository.findAllByVolunteer(volunteer);
    }
}
