package com.internship.tmontica.user.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponseDTO {

    private String name;
    private String id;
    private String email;
    private String birthDate;
    private int point;
}
