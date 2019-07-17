package com.internship.tmontica.controller;

import com.internship.tmontica.dto.Order;
import com.internship.tmontica.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;


    /** 한명 사용자의 주문정보 내역 가져오기 */
    @GetMapping
    public List<Order> getOrderByOrderId(@RequestParam String user_id){
        System.out.println("orders controller in");
        return orderService.getOrderByOrderId(user_id);
    }
}
