package com.internship.tmontica.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserChangePasswordDTO {

    private String id;
    private String password;
}
