package com.internship.tmontica.user.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserChangePasswordRequestDTO {

    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{6,19}")
    @NotNull
    private String password;
    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{6,19}")
    @NotNull
    private String passwordCheck;

}
