package com.internship.tmontica.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartReq {
    @NotNull
    private String userId;
    @NotNull
    private int menuId;
    @NotNull
    private int quantity;

    private Options options;
}
