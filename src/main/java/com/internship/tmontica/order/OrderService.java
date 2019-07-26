package com.internship.tmontica.order;

import com.internship.tmontica.cart.CartMenu;
import com.internship.tmontica.cart.CartMenuDao;
import com.internship.tmontica.menu.MenuDao;
import com.internship.tmontica.option.Option;
import com.internship.tmontica.option.OptionDao;
import com.internship.tmontica.order.exception.NotEnoughStockException;
import com.internship.tmontica.order.model.request.OrderReq;
import com.internship.tmontica.order.model.request.Order_MenusReq;
import com.internship.tmontica.order.model.response.OrderListResp;
import com.internship.tmontica.order.model.response.OrderResp;
import com.internship.tmontica.order.model.response.Order_MenusResp;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderDao orderDao;
    private final CartMenuDao cartMenuDao;
    private final MenuDao menuDao;
    private final OptionDao optionDao;
    private final JwtService jwtService;


    // 주문내역 가져오기 api
    public Map<String, List> getOrderListApi(){
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        List<OrderListResp> orderListResps = new ArrayList<>(); // 최종 반환할 리스트

        List<Order> orders = orderDao.getOrderByUserId(userId);
        for (Order order: orders) {
            OrderListResp orderListResp = new OrderListResp();

            orderListResp.setOrderId(order.getId());
            orderListResp.setOrderDate(order.getOrderDate());
            orderListResp.setStatus(order.getStatus());
            orderListResp.setMenuNames(orderDao.getMenuNamesByOrderId(order.getId()));

            orderListResps.add(orderListResp);
        }

        Map<String, List> map = new HashMap<>();

        map.put("orders", orderListResps);

        return map;
    }

    // 결제하기 api
    @Transactional(rollbackFor = NotEnoughStockException.class)
    public Map<String, Integer> addOrderApi(OrderReq orderReq){
        //토큰에서 유저아이디
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        // TODO: totalprice 여기서 한번더 계산이 필요한가?
        Order order = new Order(0,orderReq.getPayment(),orderReq.getTotalPrice(),orderReq.getUsedPoint(),
                orderReq.getTotalPrice()-orderReq.getUsedPoint(), "미결제", userId);

        // 주문테이블에 추가
        orderDao.addOrder(order);
        int orderId = order.getId();

        // 주문상세테이블에 추가
        // 카트 아이디로 정보를 가져와서 order_details 에 추가
        List<Order_MenusReq> menus = orderReq.getMenus();
        for (Order_MenusReq menu: menus) {
            CartMenu cartMenu = cartMenuDao.getCartMenuByCartId(menu.getCartId());
            int price = (menuDao.getMenuById(cartMenu.getMenuId()).getSellPrice()) + cartMenu.getPrice();// 옵션 + 메뉴 가격
            OrderDetail orderDetail = new OrderDetail(0, orderId, cartMenu.getOption(), price, cartMenu.getQuantity(),cartMenu.getMenuId());

            int stock = menuDao.getMenuById(orderDetail.getMenuId()).getStock(); // 메뉴의 현재 재고량
            int quantity = orderDetail.getQuantity(); // 차감할 메뉴의 수량
            if(stock-quantity < 0){ // 재고가 모자랄 경우 rollback
                throw new NotEnoughStockException();
            }
            // 재고가 남아있는 경우
            // 재고 수량 차감
            menuDao.updateMenuStock(orderDetail.getMenuId(), stock - quantity);
            // 주문 상세 테이블에 추가
            orderDetail.setOrderId(orderId); // 주문번호 set
            orderDao.addOrderDetail(orderDetail);
            // 장바구니에서는 삭제
            cartMenuDao.deleteCartMenu(menu.getCartId());
            // 주문상태로그테이블에 "미결제" 상태로 추가
            orderDao.addOrderStatusLog(new OrderStatusLog("미결제", userId, orderId));

        }
        Map<String, Integer> map = new HashMap<>(); // 리턴할 객체
        map.put("orderId", orderId); // 반환값 orderId
        return map;
    }

    // 주문 정보 1개 가져오기(상세내역 포함) api
    public OrderResp getOneOrderApi(int orderId){
        Order order = orderDao.getOrderByOrderId(orderId);
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        // 유저 아이디 검사
        if(!userId.equals(order.getUserId())){
            //return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<Order_MenusResp> menus = orderDao.getOrderDetailByOrderId(orderId);

        //메뉴 옵션 "1__1/4__2" => "HOT/샷추가(2개)" 로 바꾸는 작업
        for (int i = 0; i < menus.size(); i++) {
            String option = menus.get(i).getOption();
            String convert = ""; // 변환할 문자열

            String[] arrOption = option.split("/");
            for (int j = 0; j < arrOption.length; j++) {
                String[] oneOption = arrOption[j].split("__");
                Option tmpOption = optionDao.getOptionById(Integer.valueOf(oneOption[0]));
                if (j != 0) {
                    convert += "/";
                }
                if (tmpOption.getType().equals("Temperature")) {
                    convert += tmpOption.getName();
                } else if(tmpOption.getType().equals("Shot")){
                    convert += "샷추가" + "(" + oneOption[1] + "개)";
                } else if(tmpOption.getType().equals("Syrup")){
                    convert += "시럽추가" + "(" + oneOption[1] + "개)";
                } else if(tmpOption.getType().equals("Size")){
                    convert += "사이즈업" + "(" + oneOption[1] + "개)";
                }
            }

            menus.get(i).setOption(convert);
        }

        OrderResp orderResp = new OrderResp(orderId, order.getPayment(), order.getStatus(), order.getTotalPrice(),
                order.getRealPrice(), order.getUsedPoint(), order.getOrderDate(), menus);
        return orderResp;
    }

    // 주문 취소 api
    public void cancelOrderApi(int orderId){
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        String dbUserId = orderDao.getOrderByOrderId(orderId).getUserId();
        if(!userId.equals(dbUserId)){
            // 아이디 디비와 다를경우 예외처리
        }
        // orders 테이블에서 status 수정
        orderDao.deleteOrder(orderId);
        // order_status_log 테이블에도 주문취소 로그 추가
        OrderStatusLog orderStatusLog = new OrderStatusLog("주문취소", userId, orderId);
        orderDao.addOrderStatusLog(orderStatusLog);
    }
}
