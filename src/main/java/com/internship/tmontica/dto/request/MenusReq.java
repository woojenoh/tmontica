package com.internship.tmontica.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MenusReq {
    @NotNull
    private int cartId;
    @NotNull
    private String nameEng;

    private String option;
}
