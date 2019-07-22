package com.internship.tmontica.user.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
public class UserSignUpReqDTO{

    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private Date birthDate;
    @NotNull
    private String password;
    @NotNull
    private String passwordCheck;
    private String role;
}
