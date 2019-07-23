package com.internship.tmontica.cart.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartUpdateReq {
    @NotNull
    private int quantity;
}
