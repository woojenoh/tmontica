package com.internship.tmontica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResp {

    private int orderId;
    private String payment;
    private String status;
    private int totalPrice;
    private int realPrice;
    private int usedPoint;
    private Date orderDate;
    private List<Order_MenusResp> menus;
}
