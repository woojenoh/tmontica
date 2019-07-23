package com.internship.tmontica.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartReq {
    @NotNull
    private String userId;
    @NotNull
    private int menuId;
    @NotNull
    private int quantity;
    private List<Cart_OptionReq> option;
    @NotNull
    private boolean direct;
}
