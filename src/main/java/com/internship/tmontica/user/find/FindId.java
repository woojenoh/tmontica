package com.internship.tmontica.user.find;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class FindId {

    private int id;
    private String authCode;
    @NotNull
    private String findIds;

    public FindId(String authCode, String findIds){
        this.authCode = authCode;
        this.findIds = findIds;
    }

}
