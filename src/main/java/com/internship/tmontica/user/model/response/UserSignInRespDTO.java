package com.internship.tmontica.user.model.response;

import com.internship.tmontica.util.UserConfigValueName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSignInRespDTO {

    private String result;
    private String authorization;
}
