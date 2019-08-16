package com.internship.tmontica.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserFindIdResponseDTO {

    private List<String> userIdList;
}
