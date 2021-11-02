package com.berrygood.domain.volunteer.api.response;

import com.berrygood.domain.volunteer.entity.Volunteer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerResponse {
    Volunteer volunteer;
    List<String> imgPath;
    int totalCnt;
}
