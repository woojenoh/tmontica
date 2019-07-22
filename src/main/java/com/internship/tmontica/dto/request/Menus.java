package com.internship.tmontica.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Menus {
    @NotNull
    private int cartId;

}
