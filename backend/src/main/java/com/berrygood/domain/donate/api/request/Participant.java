package com.berrygood.domain.donate.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant{
	private Long projectId;
    private String userEmail;
    private Long amount;
	private String message;
}
