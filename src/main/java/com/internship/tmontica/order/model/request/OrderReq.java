package com.internship.tmontica.order.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderReq {
    @NotEmpty
    @Valid
    private List<OrderMenusReq> menus;
    private int usedPoint;
    @Min(1000)
    private int totalPrice;
    @NotEmpty
    private String payment;

}
