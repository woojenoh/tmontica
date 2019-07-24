package com.internship.tmontica.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserFindIdRespDTO {

    private String id;
    private boolean success;
}
