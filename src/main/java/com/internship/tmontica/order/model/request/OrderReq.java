package com.internship.tmontica.order.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderReq {
    @NotNull
    private String userId;
    @NotNull
    private List<Order_MenusReq> menus;
    private int usedPoint;
    @NotNull
    private int totalPrice;
    @NotNull
    private String payment;

}
