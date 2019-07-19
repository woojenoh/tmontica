package com.internship.tmontica.user.model.request;

import com.internship.tmontica.user.model.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserSignInReqDTO implements UserDTO {

    @NotNull
    private String id;
    @NotNull
    private String password;
}
