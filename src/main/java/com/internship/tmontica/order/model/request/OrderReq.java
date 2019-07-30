package com.internship.tmontica.order.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderReq {
    @NotNull
    private List<Order_MenusReq> menus;
    private int usedPoint;
    @NotNull
    private int totalPrice;
    @NotEmpty
    private String payment;

}
