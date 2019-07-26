package com.internship.tmontica.order;

import com.internship.tmontica.order.model.request.OrderReq;
import com.internship.tmontica.order.model.response.OrderResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /** 주문 받기(결제하기) */
    @PostMapping
    public ResponseEntity<Map<String, Integer>> addOrder(@RequestBody @Valid OrderReq orderReq){
        Map<String, Integer> map = orderService.addOrderApi(orderReq);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /** 주문 취소 */
    @DeleteMapping("/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable("orderId") int orderId){
        orderService.cancelOrderApi(orderId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /** 주문정보 한개 가져오기(상세내역 포함) */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResp> getOrderByOrderId(@PathVariable("orderId")int orderId){
        OrderResp orderResp  = orderService.getOneOrderApi(orderId);
        return new ResponseEntity(orderResp, HttpStatus.OK);
    }


    /** 사용자 한명의 주문 전체 내역 가져오기 */
    @GetMapping
    public ResponseEntity<Map<String, List>> getOrderList(){
        Map<String, List> map = orderService.getOrderListApi();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
