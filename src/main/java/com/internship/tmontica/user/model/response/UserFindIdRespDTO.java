package com.internship.tmontica.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserFindIdRespDTO {

    private String userIdList;
    private boolean success;
}
