package com.berrygood.domain.volunteer.repository;

import com.berrygood.domain.volunteer.entity.Volunteer;
import com.berrygood.domain.volunteer.entity.VolunteerImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerImgRepository extends JpaRepository<VolunteerImg, Long> {
    // 특정 활동에 대한 이미지 전체 반환
    Optional<List<VolunteerImg>> findAllByVolunteer(Volunteer volunteer);

    // 삭제
    @Transactional
    void deleteAllByVolunteer(Volunteer volunteer);
}
