package com.internship.tmontica.controller;

import com.internship.tmontica.dto.*;
import com.internship.tmontica.dto.request.*;
import com.internship.tmontica.dto.response.MenusResp;
import com.internship.tmontica.dto.response.OrderListResp;
import com.internship.tmontica.dto.response.OrderResp;
import com.internship.tmontica.service.CartMenuService;
import com.internship.tmontica.service.MenuService;
import com.internship.tmontica.service.OptionService;
import com.internship.tmontica.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private CartMenuService cartMenuService;
    @Autowired
    private MenuService menuService;

    /** 주문 받기(결제하기) */
    @PostMapping
    public Map<String, Integer> addOrder(@RequestBody @Valid OrderReq orderReq){
        System.out.println("결제 컨트롤러 ");
        System.out.println(orderReq);

        // 사용자 아이디 받아오기
        String userId = "testid"; //임시

        Map<String, Integer> map = new HashMap<>(); // 리턴할 객체

        Order order = new Order(0,orderReq.getPayment(),orderReq.getTotalPrice(),orderReq.getUsedPoint(),
                orderReq.getTotalPrice()-orderReq.getUsedPoint(), "미결제", orderReq.getUserId());
        int orderId = 0;

        // 주문상세테이블에 추가
        // menus에서 카트 아이디로 정보를 가져와서 order_details 에 추가
        List<Menus> menus = orderReq.getMenus();
        for (Menus menu: menus) {
            OrderDetail orderDetail = cartMenuService.getCartMenuForOrderDetail(menu.getCartId());

            // TODO: 주문하는 메뉴의 재고 차감
            int stock = menuService.getMenuById(orderDetail.getMenuId()).getStock(); // 메뉴의 현재 재고량
            int quantity = orderDetail.getQuantity(); // 차감할 메뉴의 수량
            if(stock-quantity <= 0){ // 재고가 모자랄 경우
                // TODO: 재고 없을 경우 반환값 정하기, 위에 insert 다시 다 되돌려야 함..
                map.put("stock", -1);
                return map;
            }else {                 // 재고가 남아있는 경우
                // 재고 수량 업데이트
                menuService.updateMenuStock(orderDetail.getMenuId(), stock-quantity);
                // 주문테이블에 추가
                orderService.addOrder(order);
                orderId = order.getId();
                orderDetail.setOrderId(orderId); // 주문번호 set
                // 주문 상세 테이블에 추가
                orderService.addOrderDetail(orderDetail);
                // 장바구니에서는 삭제
                cartMenuService.deleteCartMenu(menu.getCartId());
            }
        }

        // 주문상태로그테이블에 "미결제" 상태로 추가
        orderService.addOrderStatusLog(new OrderStatusLog("미결제", userId, orderId));

        map.put("orderId", orderId);
        return map;
    }

    /** 주문 받기(결제하기) - 바로주문하기 */
    /*@PostMapping("/direct")
    public Map<String, String> addOrderDirect(@RequestBody @Valid CartReq cartReq){
        // TODO: 카트에 추가하고
        // options 안의 옵션 정보들을 "1__1/3__2"(id__갯수) 형태의 문자열로 만들기
        String optionStr = ""; // 옵션 문자열
        int optionPrice = 0; // 옵션의 총 가격을 저장할 변수
        Options options = cartReq.getOptions();
        Temperature temperature = options.getTemperature();
        int opId = optionService.getOptionIdByName(temperature.getName());
        optionStr += opId+"__1";
        if(options.getSize() != null){
            Size size = options.getSize();
            opId = optionService.getOptionIdByName(size.getName());
            optionStr += "/"+opId+"__1";
            optionPrice += size.getPrice();
        }
        if(options.getShot() != null){
            Shot shot = options.getShot();
            opId = optionService.getOptionIdByName(shot.getName());
            optionStr += "/"+opId+"__"+shot.getAmount();
            optionPrice += (shot.getPrice() * shot.getAmount());
        }
        if(options.getSyrup() != null){
            Syrup syrup = options.getSyrup();
            opId = optionService.getOptionIdByName(syrup.getName());
            optionStr += "/"+opId+"__"+syrup.getAmount();
            optionPrice += (syrup.getPrice() * syrup.getAmount());
        }

        // 사용자 아이디 받아오기
        String userId = "testid"; //임시

        // 주문번호 생성 : 날짜 + 시분초 + 아이디
        SimpleDateFormat format  = new SimpleDateFormat("yyMMddHHmmss");
        String orderId = format.format(System.currentTimeMillis()) + userId;
        System.out.println(orderId);

    }*/

    /** 주문 취소 */
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") int orderId){
        // 컬럼을 지우는게 아니라 status를 주문취소로 수정하는것임
        // orders 테이블에서 status 수정
        orderService.deleteOrder(orderId);
        // order_status_log 테이블에도 주문취소 로그 추가
        // TODO: 토큰에서? 사용자 아이디 가져오기 해야함
        OrderStatusLog orderStatusLog = new OrderStatusLog("주문취소","사용자id", orderId);
        orderService.addOrderStatusLog(orderStatusLog);
    }

    /** 주문정보 한개 가져오기(상세내역 포함) */
    @GetMapping("/{orderId}")
    public OrderResp getOrderByOrderId(@PathVariable("orderId")int orderId){
        // TODO: menus에 들어갈 객체 필요, menu select 기능 필요
        // TODO: 주문번호로 주문정보와 주문 상세정보를 객체에 담아 리턴시키
        Order order = orderService.getOrderByOrderId(orderId);
        List<MenusResp> menus = orderService.getOrderDetailByOrderId(orderId);

        //메뉴 옵션 "1__1/4__2" => "HOT/샷추가(2개)" 로 바꾸는 작업
        for (int i = 0; i < menus.size(); i++) {
            String option = menus.get(i).getOption();
            String convert = ""; // 변환할 문자열
            String[] arrOption = option.split("/");
            for (int j = 0; j < arrOption.length; j++){
               String[] oneOption = arrOption[j].split("__");
               Option tmpOption = optionService.getOptionById(Integer.valueOf(oneOption[0]));
               if(j != 0 ){ convert += "/"; }
               if(tmpOption.getType().equals("Temperature")){
                   convert += tmpOption.getName();
               }else{
                   convert += tmpOption.getName()+"("+oneOption[1]+"개)";
               }
            }
            menus.get(i).setOption(convert);
        }

        OrderResp orderResp = new OrderResp(orderId, order.getPayment(), order.getStatus(), order.getTotalPrice(),
                                            order.getRealPrice(), order.getUsedPoint(), order.getOrderDate(), menus);

        // 더미 데이터
//        List<MenusResp> menus = new ArrayList<>();
//        menus.add(new MenusResp(1, "americano", "HOT / 샷추가(1개) / SIZE UP", 3, 3800));
//        menus.add(new MenusResp(2, "caffelatte", "ICE / 샷추가(1개) / SIZE UP", 1, 2300));
//        OrderResp orderResp = new OrderResp("현장결제","미결제",6100,4000,2100,
//                Date.valueOf("2019-07-19") ,menus);

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
        //System.out.println(orderListResps);

        Map<String, List> map = new HashMap<>();

        map.put("orders", orderListResps);

        return map;
    }
}
