package com.internship.tmontica.cart.model.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class Cart_OptionReq {
    @Min(1)
    private int id;
    @Min(1)
    private int quantity;
}
