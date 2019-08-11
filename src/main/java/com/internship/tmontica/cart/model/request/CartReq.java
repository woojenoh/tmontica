package com.internship.tmontica.cart.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class CartReq {

    @Min(1)
    private int menuId;
    @Min(1)
    private int quantity;
    @Valid
    private List<CartOptionReq> option;
    @NotNull
    private Boolean direct; // @NotNull 적용을 위해
}
