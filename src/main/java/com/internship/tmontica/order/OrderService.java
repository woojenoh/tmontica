package com.internship.tmontica.order;

import com.internship.tmontica.order.model.response.Order_MenusResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderDao orderDao;

    // user_id로 주문 정보 가져오기
    @Transactional(readOnly = true)
    public List<Order> getOrderByUserId(String userId){
        return orderDao.getOrderByUserId(userId);
    }

    // orderId로 주문 정보 가져오기
    @Transactional(readOnly = true)
    public Order getOrderByOrderId(int orderId){ return orderDao.getOrderByOrderId(orderId); }

    // orderId로 주문상세 정보 가져오기 (메뉴 이름 추가)
    @Transactional(readOnly = true)
    public List<Order_MenusResp> getOrderDetailByOrderId(int orderId){
        return orderDao.getOrderDetailByOrderId(orderId);
    }

    // order_id로 주문의 메뉴 이름들만 가져오기
    @Transactional(readOnly = true)
    public List<String> getMenuNamesByOrderId(int orderId){
        return orderDao.getMenuNamesByOrderId(orderId);
    }

    // order_id로 주문 삭제하기(상태만 바꾸기)
    public void deleteOrder(int orderId){
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
