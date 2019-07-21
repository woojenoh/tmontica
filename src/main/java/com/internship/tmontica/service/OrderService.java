package com.internship.tmontica.service;

import com.internship.tmontica.dto.CartMenu;
import com.internship.tmontica.dto.Order;
import com.internship.tmontica.dto.OrderDetail;
import com.internship.tmontica.dto.OrderStatusLog;
import com.internship.tmontica.dto.response.MenusResp;
import com.internship.tmontica.repository.OrderDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;

    // user_id로 주문 정보 가져오기
    public List<Order> getOrderByUserId(String userId){
        return orderDao.getOrderByUserId(userId);
    }

    // orderId로 주문 정보 가져오기
    public Order getOrderByOrderId(String orderId){ return orderDao.getOrderByOrderId(orderId); }

    // orderId로 주문상세 정보 가져오기 (메뉴 이름 추가)
    public List<MenusResp> getOrderDetailByOrderId(String orderId){
        return orderDao.getOrderDetailByOrderId(orderId);
    }

    // order_id로 주문의 메뉴 이름들만 가져오기
    public List<String> getMenuNamesByOrderId(String orderId){
        return orderDao.getMenuNamesByOrderId(orderId);
    }

    // order_id로 주문 삭제하기
    public void deleteOrder(String orderId){
        orderDao.deleteOrder(orderId);
    }

    // order_status_log 추가
    public int addOrderStatusLog(OrderStatusLog orderStatusLog){
        return orderDao.addOrderStatusLog(orderStatusLog);
    }

    // 주문테이블에 추가
    public int addOrder(Order order){
        return orderDao.addOrder(order);
    }

    // 주문 상세 테이블에 추가
    public int addOrderDetail(OrderDetail orderDetail){ return orderDao.addOrderDetail(orderDetail); }

}
