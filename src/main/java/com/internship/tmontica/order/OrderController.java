package com.internship.tmontica.order;

import com.internship.tmontica.order.exception.OrderExceptionType;
import com.internship.tmontica.order.exception.OrderValidException;
import com.internship.tmontica.order.model.request.OrderReq;
import com.internship.tmontica.order.model.request.OrderStatusReq;
import com.internship.tmontica.order.model.response.OrderDetailResp;
import com.internship.tmontica.order.model.response.OrderResp;
import com.internship.tmontica.order.model.response.OrdersByStatusResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<Map<String, Integer>> addOrder(@RequestBody @Valid OrderReq orderReq, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            throw new OrderValidException(OrderExceptionType.INVALID_ORDER_ADD_FORM, bindingResult);
        }
        Map<String, Integer> map = orderService.addOrderApi(orderReq);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /** 주문 취소(사용자) */
    @DeleteMapping("/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable("orderId") int orderId){
        orderService.cancelOrderApi(orderId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /** 주문정보 한개 가져오기(상세내역 포함) */
    @GetMapping("/{orderId:\\d+}")
    public ResponseEntity<OrderResp> getOrderByOrderId(@PathVariable("orderId")int orderId){
        OrderResp orderResp  = orderService.getOneOrderApi(orderId);
        return new ResponseEntity<>(orderResp, HttpStatus.OK);
    }


    /** 사용자 한명의 주문 전체 내역 가져오기 */
    @GetMapping
    public ResponseEntity<Map<String, List>> getOrderList(){
        Map<String, List> map = orderService.getOrderListApi();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /** 주문 상태 바꾸기(관리자) */
    @PutMapping("/{orderId}/status")
    public ResponseEntity updateOrderStatus(@PathVariable("orderId")int orderId, @RequestBody @Valid OrderStatusReq orderStatusReq, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            throw new OrderValidException(OrderExceptionType.INVALID_STATUS_FORM, bindingResult);
        }
        orderService.updateOrderStatusApi(orderId, orderStatusReq);
        return new ResponseEntity(HttpStatus.OK);
    }

    /** 주문 상태별로 주문 정보 가져오기(관리자) */
    @GetMapping("/{status:[A-Z]+_?[A-Z]+}")
    public ResponseEntity<List<OrdersByStatusResp>> getOrderByStatus(@PathVariable("status")String status){
        List<OrdersByStatusResp> ordersByStatusResps = orderService.getOrderByStatusApi(status);
        return new ResponseEntity<>(ordersByStatusResps, HttpStatus.OK);
    }

    /** 주문 상세 정보 가져오기(관리자) */
    @GetMapping("/detail/{orderId}")
    public ResponseEntity<OrderDetailResp> getOrderDetail(@PathVariable("orderId")int orderId){
        OrderDetailResp orderDetailResp = orderService.getOrderDetailApi(orderId);
        return new ResponseEntity<>(orderDetailResp, HttpStatus.OK);
    }
}
