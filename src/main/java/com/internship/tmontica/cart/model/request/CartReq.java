package com.internship.tmontica.cart.model.request;

import com.internship.tmontica.cart.model.request.Cart_OptionReq;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartReq {

    @NotNull
    private int menuId;
    @NotNull
    @Min(1)
    private int quantity;
    private List<Cart_OptionReq> option;
    @NotNull
    private boolean direct;
}
