package com.internship.tmontica.user.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserFindIdRequsetDTO {

    @NotNull
    private String authCode;
}
