package com.internship.tmontica.user.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserSignInReqDTO{

    @NotNull
    private String id;
    @NotNull
    private String password;
}
