package com.internship.tmontica.order;

import com.internship.tmontica.cart.CartMenu;
import com.internship.tmontica.cart.CartMenuDao;
import com.internship.tmontica.cart.CartMenuService;
import com.internship.tmontica.menu.MenuDao;
import com.internship.tmontica.option.Option;
import com.internship.tmontica.option.OptionDao;
import com.internship.tmontica.order.exception.NotEnoughStockException;
import com.internship.tmontica.order.model.request.OrderReq;
import com.internship.tmontica.order.model.request.OrderStatusReq;
import com.internship.tmontica.order.model.request.Order_MenusReq;
import com.internship.tmontica.order.model.response.*;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.user.UserRole;
import com.internship.tmontica.user.exception.UserException;
import com.internship.tmontica.user.exception.UserExceptionType;
import com.internship.tmontica.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final JwtService jwtService;
    private final CartMenuService cartMenuService;


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
    @Transactional(rollbackFor = {NotEnoughStockException.class, UserException.class} )
    public Map<String, Integer> addOrderApi(OrderReq orderReq){
        //토큰에서 유저아이디
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        // TODO: totalprice 여기서 한번더 계산이 필요한가?
        Order order = new Order(0,orderReq.getPayment(),orderReq.getTotalPrice(),orderReq.getUsedPoint(),
                orderReq.getTotalPrice()-orderReq.getUsedPoint(), OrderStatusType.BEFORE_PAYMENT.toString(), userId);
        // 주문테이블에 추가
        orderDao.addOrder(order);
        int orderId = order.getId();

        // 주문상세테이블에 추가
        // 카트 아이디로 정보를 가져와서 order_details 에 추가
        List<Order_MenusReq> menus = orderReq.getMenus();
        for (Order_MenusReq menu: menus) {
            CartMenu cartMenu = cartMenuDao.getCartMenuByCartId(menu.getCartId());

            // 로그인한 아이디와 디비의 아이디 다르면 rollback
            if(!userId.equals(cartMenu.getUserId())){
                throw new UserException(UserExceptionType.INVALID_USER_ID_EXCEPTION);
            }

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
            orderDao.addOrderStatusLog(new OrderStatusLog(OrderStatusType.BEFORE_PAYMENT.toString(), userId, orderId));

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
            throw new UserException(UserExceptionType.INVALID_USER_ID_EXCEPTION);
        }

        List<Order_MenusResp> menus = orderDao.getOrderDetailByOrderId(orderId);

        //메뉴 옵션 "1__1/4__2" => "HOT/샷추가(2개)" 로 바꾸는 작업
        for (Order_MenusResp menu : menus) {
            String option = menu.getOption();
            String convert = cartMenuService.convertOptionStringToCli(option); // 변환할 문자열

            menu.setOption(convert);
        }

        OrderResp orderResp = new OrderResp(orderId, order.getPayment(), order.getStatus(), order.getTotalPrice(),
                order.getRealPrice(), order.getUsedPoint(), order.getOrderDate(), menus);
        return orderResp;
    }

    // 주문 취소 api(사용자)
    public void cancelOrderApi(int orderId){
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        String dbUserId = orderDao.getOrderByOrderId(orderId).getUserId();
        if(!userId.equals(dbUserId)){
            // 아이디 디비와 다를경우 예외처리
            throw new UserException(UserExceptionType.INVALID_USER_ID_EXCEPTION);
        }
        // orders 테이블에서 status 수정
        orderDao.updateOrderStatus(orderId, OrderStatusType.CANCEL.toString());
        // order_status_log 테이블에도 주문취소 로그 추가
        OrderStatusLog orderStatusLog = new OrderStatusLog(OrderStatusType.CANCEL.toString(), userId, orderId);
        orderDao.addOrderStatusLog(orderStatusLog);
    }

    // 주문 상태 변경 api(관리자)
    public void updateOrderStatusApi(int orderId, OrderStatusReq orderStatusReq){
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        String role = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"role");
        if(!role.equals(UserRole.ADMIN.toString())){
            throw new UserException(UserExceptionType.INVALID_USER_ROLE_EXCEPTION);
        }
        // orders 테이블에서 status 수정
        orderDao.updateOrderStatus(orderId, orderStatusReq.getStatus());
        // order_status_log 테이블에도 로그 추가
        OrderStatusLog orderStatusLog = new OrderStatusLog(orderStatusReq.getStatus(), userId, orderId);
        orderDao.addOrderStatusLog(orderStatusLog);
    }

    // 주문 상태별로 주문정보 가져오기 api(관리자)

    
    // 주문 상세정보 가져오기 api(관리자)
    public OrderDetailResp getOrderDetailApi(int orderId){
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        String role = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"role");
        if(!role.equals(UserRole.ADMIN.toString())){
            throw new UserException(UserExceptionType.INVALID_USER_ROLE_EXCEPTION);
        }

        Order order = orderDao.getOrderByOrderId(orderId);
        List<Order_MenusResp> menus = orderDao.getOrderDetailByOrderId(orderId);
        //메뉴 옵션 "1__1/4__2" => "HOT/샷추가(2개)" 로 바꾸는 작업
        for (Order_MenusResp menu : menus) {
            String option = menu.getOption();
            String convert = cartMenuService.convertOptionStringToCli(option); // 변환할 문자열

            menu.setOption(convert);
        }
        List<OrderStatusLogResp> orderStatusLogs = orderDao.getOrderStatusLogByOrderId(orderId);

        OrderDetailResp orderDetailResp = new OrderDetailResp(order.getUserId(), orderId, order.getTotalPrice(),menus, orderStatusLogs);
        return orderDetailResp;
    }
}
