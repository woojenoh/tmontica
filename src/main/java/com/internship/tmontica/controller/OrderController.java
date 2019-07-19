package com.internship.tmontica.controller;

import com.internship.tmontica.dto.Order;
import com.internship.tmontica.dto.OrderStatusLog;
import com.internship.tmontica.dto.request.OrderReq;
import com.internship.tmontica.dto.response.MenusResp;
import com.internship.tmontica.dto.response.OrderListResp;
import com.internship.tmontica.dto.response.OrderResp;
import com.internship.tmontica.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /** 주문 받기(결제하기) */
    @PostMapping
    public int addOrder(@RequestBody @Valid OrderReq orderReq){
        // TODO: 주문테이블에 추가
        // TODO: 주문상세테이블에 추가
        // TODO: 주문상태로그테이블에 추가
        // TODO: 장바구니에서 주문할때는 장바구니에서는 삭제처리 -> 어디서 해줄지?

        // TODO: menus에 들어갈 객체 만들어서 구현해야 함
        // TODO: 주문번호 생성해서 리턴해주기
        // TODO: 주문받을때 menus 안에 quantity필요할듯

        return 1;
    }

    /** 주문 취소 */
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") int orderId){
        // 컬럼을 지우는게 아니라 status를 주문취소로 수정하는것임
        // orders 테이블에서 status 수정
        orderService.deleteOrder(orderId);
        // order_status_log 테이블에도 로그 추가
        // TODO: 사용자 아이디 가져오기 해야함
        OrderStatusLog orderStatusLog = new OrderStatusLog("주문취소","사용자id", orderId);
        orderService.addOrderStatusLog(orderStatusLog);
    }

    /** 주문정보 한개 가져오기(상세내역 포함) */
    @GetMapping("/{orderId}")
    public OrderResp getOrderByOrderId(@PathVariable("orderId")int orderId){
        // TODO: menus에 들어갈 객체 필요, menu select 기능 필요
        // TODO: 주문번호로 주문정보와 주문 상세정보를 객체에 담아 리턴시키
//        Order order = orderService.getOrderByOrderId(orderId);
//        List<MenusResp> menus = orderService.getOrderDetailByOrderId(orderId);
//
//        //메뉴 옵션 "4__2" => "샷추가(2개)" 로 바꾸는 작업
//        for (int i = 0; i < menus.size(); i++) {
//            String option = menus.get(i).getOption();
//            String[] arrOption = option.split("/");
//            for (int j = 0; j < arrOption.length; j++){
//                 = arrOption[j].split("__");
//            }
//        }
//
//        OrderResp orderResp = new OrderResp(order.getPayment(), order.getStatus(), order.getTotalPrice(),
//                                            order.getRealPrice(), order.getUsedPoint(), order.getOrderDate(), menus);


        // 더미 데이터
        List<MenusResp> menus = new ArrayList<>();
        menus.add(new MenusResp(1, "americano", "HOT / 샷추가(1개) / SIZE UP", 3, 3800));
        menus.add(new MenusResp(2, "caffelatte", "ICE / 샷추가(1개) / SIZE UP", 1, 2300));
        OrderResp orderResp = new OrderResp("현장결제","미결제",6100,4000,2100,
                Date.valueOf("2019-07-19") ,menus);

        return orderResp;
    }


    /** 사용자 한명의 주문 전체 내역 가져오기 */
    @GetMapping
    public Map<String, List> getOrderByUserId(@RequestParam String userId){
        List<OrderListResp> orderListResps = new ArrayList<>(); // 최종 반환할 리스트

        List<Order> orders = orderService.getOrderByUserId(userId);
        for (Order order: orders) {
            OrderListResp orderListResp = new OrderListResp();

            orderListResp.setOrderID(order.getId());
            orderListResp.setOrderDate(order.getOrderDate());
            orderListResp.setStatus(order.getStatus());
            orderListResp.setMenuNames(orderService.getMenuNamesByOrderId(order.getId()));

            orderListResps.add(orderListResp);
        }
        System.out.println(orderListResps);

        Map<String, List> map = new HashMap<>();

        //map.put("orders", orderService.getOrderByUserId(user_id));
        map.put("orders", orderListResps);

        return map;
    }
}
