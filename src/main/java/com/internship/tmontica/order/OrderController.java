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
        // 결제 처리
        Map<String, Integer> map = orderService.addOrderApi(orderReq);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /** 주문 취소 */
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") int orderId){
        // orders 테이블에서 status 수정
        orderService.deleteOrder(orderId);
        // order_status_log 테이블에도 주문취소 로그 추가
        // TODO: 토큰에서? 사용자 아이디 가져오기 해야함
        OrderStatusLog orderStatusLog = new OrderStatusLog("주문취소","사용자id", orderId);
        orderService.addOrderStatusLog(orderStatusLog);
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
