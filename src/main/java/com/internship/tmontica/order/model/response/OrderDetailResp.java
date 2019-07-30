package com.internship.tmontica.order.model.response;

import com.internship.tmontica.order.OrderStatusLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResp {
    String userId; // 주문한 유저 아이디
    int orderId;
    int totalPrice;
    List<Order_MenusResp> menus;
    List<OrderStatusLogResp> orderStatusLogs;
}
