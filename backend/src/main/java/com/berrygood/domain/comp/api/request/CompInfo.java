package com.berrygood.domain.comp.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CompInfo {
	private String email;
	private String name;
    private String phone;
    private String address;
}
