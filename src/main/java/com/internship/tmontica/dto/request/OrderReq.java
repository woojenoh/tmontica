package com.internship.tmontica.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderReq {
    @NotNull
    private String userId;
    @NotNull
    private List<Menus> menus;
    private int usedPoint;
    @NotNull
    private int totalPrice;
    @NotNull
    private String payment;

}
