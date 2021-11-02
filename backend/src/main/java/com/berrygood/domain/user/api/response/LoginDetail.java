package com.berrygood.domain.user.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.HashMap;

@Setter @Builder
@ApiModel("LoginInfo")
public class LoginDetail {
    @ApiModelProperty(name="사용자 ID", example="a@ssafy.com")
    String email;

    @ApiModelProperty(name="사용자 정보", example="[user, company]")
    String role;

    public static HashMap<?,?> of(String email, String role) {
        HashMap<String, String> res = new HashMap<>();
        res.put("email", email);
        res.put("role", role);
        return res;
    }
}
