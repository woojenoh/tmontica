package com.internship.tmontica.service;

import com.internship.tmontica.dto.Order;
import com.internship.tmontica.repository.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    /** 한명 사용자의 주문정보 가져오기 */
    @GetMapping
    public List<Order> getOrderByOrderId(String user_id){
        return orderDao.getOrderByOrderId(user_id);
    }
}
