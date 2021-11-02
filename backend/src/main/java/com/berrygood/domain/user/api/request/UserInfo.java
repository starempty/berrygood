package com.berrygood.domain.user.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserInfo {
    private String email;
    private String phone;
    private String address;
    private String nickname;
}
