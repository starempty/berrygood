package com.berrygood.domain.user.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel("LoginInfo")
public class LoginInfo {
    @ApiModelProperty(name="유저 email", example="a@ssafy.com")
    String email;
    @ApiModelProperty(name="유저 PW", example="password")
    String pw;
}
