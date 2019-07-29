package com.internship.tmontica.order.model.response;

import com.internship.tmontica.order.OrderStatusLog;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailResp {
    String userId; // 주문한 유저 아이디
    int orderId;
    int totalPrice;
    List<Order_MenusResp> menus;

}
