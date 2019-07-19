package com.internship.tmontica.repository;

import com.internship.tmontica.dto.Order;
import com.internship.tmontica.dto.OrderDetail;
import com.internship.tmontica.dto.OrderStatusLog;
import com.internship.tmontica.dto.response.MenusResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {

    // userId로 주문 정보 가져오기
    List<Order> getOrderByUserId(String userId);

    // orderId로 주문 정보 가져오기
    Order getOrderByOrderId(int orderId);

    // orderId로 주문상세 정보 가져오기 (메뉴 이름 추가)
    List<MenusResp> getOrderDetailByOrderId(int orderId);

    // orderId로 주문의 메뉴 이름들만 가져오기
    List<String> getMenuNamesByOrderId(int orderId);

    // orderId로 주문상태를 주문취소로 바꾸
    void deleteOrder(int orderId);

    // order_status_log 추가
    int addOrderStatusLog(OrderStatusLog orderStatusLog);

    // 주문테이블에 추가하기
    int addOrder(Order order);
}
