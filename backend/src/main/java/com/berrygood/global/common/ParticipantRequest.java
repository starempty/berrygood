package com.berrygood.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantRequest {
    private Long projectId;
    private String userEmail;
    private Long amount;
}
