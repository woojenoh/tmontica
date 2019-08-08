package com.internship.tmontica.order;

import com.internship.tmontica.cart.CartMenu;
import com.internship.tmontica.cart.CartMenuDao;
import com.internship.tmontica.cart.CartMenuService;
import com.internship.tmontica.cart.exception.CartExceptionType;
import com.internship.tmontica.cart.exception.CartException;
import com.internship.tmontica.menu.MenuDao;
import com.internship.tmontica.order.exception.NotEnoughStockException;
import com.internship.tmontica.order.exception.OrderExceptionType;
import com.internship.tmontica.order.exception.OrderException;
import com.internship.tmontica.order.exception.StockExceptionType;
import com.internship.tmontica.order.model.request.OrderReq;
import com.internship.tmontica.order.model.request.OrderMenusReq;
import com.internship.tmontica.order.model.response.OrderListResp;
import com.internship.tmontica.order.model.response.OrderResp;
import com.internship.tmontica.order.model.response.OrderMenusResp;
import com.internship.tmontica.point.Point;
import com.internship.tmontica.point.PointDao;
import com.internship.tmontica.point.PointLogType;
import com.internship.tmontica.point.exception.PointException;
import com.internship.tmontica.point.exception.PointExceptionType;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.user.UserDao;
import com.internship.tmontica.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;
    private final CartMenuDao cartMenuDao;
    private final MenuDao menuDao;
    private final UserDao userDao;
    private final PointDao pointDao;
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
    @Transactional(rollbackFor = {NotEnoughStockException.class, CartException.class, OrderException.class} )
    public Map<String, Integer> addOrderApi(OrderReq orderReq, Device device){
        //토큰에서 유저아이디
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");

        // 포인트를 사용했을 경우
        if (orderReq.getUsedPoint() > 0){
            int userPoint = userDao.getUserPointByUserId(userId);
            // 가지고 있는 포인트보다 더 사용하려고 할 때 예외처리
            if(userPoint < orderReq.getUsedPoint()){
                throw new PointException(PointExceptionType.POINT_LESS_THEN_ZERO_EXCEPTION);
            }
            // user 테이블에 사용한 포인트 차감하기
            userDao.updateUserPoint(userPoint-orderReq.getUsedPoint(), userId);

            // point 테이블에 사용 로그 추가하기
            pointDao.addPoint(new Point(userId, PointLogType.USE_POINT.getType() ,orderReq.getUsedPoint(), "결제시 사용"));
        }

        // device 종류 검사
        String userAgent = "";
        if (device.isMobile()) {
            userAgent = UserAgentType.MOBILE.toString();
        } else if (device.isTablet()) {
            userAgent = UserAgentType.TABLET.toString();
        } else {
            userAgent = UserAgentType.PC.toString();
        }

        Order order = new Order(0,orderReq.getPayment(),orderReq.getTotalPrice(),orderReq.getUsedPoint(),
                orderReq.getTotalPrice()-orderReq.getUsedPoint(), OrderStatusType.BEFORE_PAYMENT.getStatus(), userId, userAgent);
        // 주문테이블에 추가
        orderDao.addOrder(order);
        int orderId = order.getId();
        int totalPrice = 0; // 금액 체크를 위한 변수
        // 주문상세테이블에 추가
        // 카트 아이디로 정보를 가져와서 order_details 에 추가
        List<OrderMenusReq> menus = orderReq.getMenus();
        for (OrderMenusReq menu: menus) {
            CartMenu cartMenu = cartMenuDao.getCartMenuByCartId(menu.getCartId());

            // 로그인한 아이디와 해당 카트id의 userId가 다르면 rollback
            if(!userId.equals(cartMenu.getUserId())){
                throw new CartException(CartExceptionType.FORBIDDEN_ACCESS_CART_DATA);
            }

            int price = (menuDao.getMenuById(cartMenu.getMenuId()).getSellPrice()) + cartMenu.getPrice();// 옵션 + 메뉴 가격
            OrderDetail orderDetail = new OrderDetail(0, orderId, cartMenu.getOption(), price, cartMenu.getQuantity(),cartMenu.getMenuId());

            totalPrice += (price * cartMenu.getQuantity());

            int stock = menuDao.getMenuById(orderDetail.getMenuId()).getStock(); // 메뉴의 현재 재고량
            int quantity = orderDetail.getQuantity(); // 차감할 메뉴의 수량
            if(stock-quantity < 0){ // 재고가 모자랄 경우 rollback
                throw new NotEnoughStockException(cartMenu.getMenuId(), StockExceptionType.NOT_ENOUGH_STOCK);
            }
            // 재고가 남아있는 경우
            // 재고 수량 차감
            menuDao.updateMenuStock(orderDetail.getMenuId(), stock - quantity);
            // 주문 상세 테이블에 추가
            orderDetail.setOrderId(orderId); // 주문번호 set
            orderDao.addOrderDetail(orderDetail);
            // 장바구니에서는 삭제
            cartMenuDao.deleteCartMenu(menu.getCartId());

        }

        // totalPrice 맞는지 체크
        if (totalPrice != orderReq.getTotalPrice()){
            throw new OrderException(OrderExceptionType.INVALID_TOTALPRICE);
        }

        // 주문상태로그테이블에 "미결제" 상태로 추가
        orderDao.addOrderStatusLog(new OrderStatusLog(OrderStatusType.BEFORE_PAYMENT.getStatus(), userId, orderId));

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
            throw new OrderException(OrderExceptionType.FORBIDDEN_ACCESS_ORDER_DATA);
        }

        List<OrderMenusResp> menus = orderDao.getOrderDetailByOrderId(orderId);

        for (OrderMenusResp menu : menus) {
            //메뉴 옵션 "1__1/4__2" => "HOT/샷추가(2개)" 로 바꾸는 작업
            if(!menu.getOption().equals("")){
                String option = menu.getOption();
                String convert = cartMenuService.convertOptionStringToCli(option); // 변환할 문자열
                menu.setOption(convert);
            }

            // imgUrl 경로 설정
            menu.setImgUrl("/images/".concat(menu.getImgUrl()));
        }

        OrderResp orderResp = new OrderResp(orderId, order.getPayment(), order.getStatus(), order.getTotalPrice(),
                order.getRealPrice(), order.getUsedPoint(), order.getOrderDate(), menus);
        return orderResp;
    }


    // 주문 취소 api(사용자)
    @Transactional
    public void cancelOrderApi(int orderId){
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        String dbUserId = orderDao.getOrderByOrderId(orderId).getUserId();
        if(!userId.equals(dbUserId)){
            // 아이디 디비와 다를경우 예외처리
            throw new OrderException(OrderExceptionType.FORBIDDEN_ACCESS_ORDER_DATA);
        }

        Order order = orderDao.getOrderByOrderId(orderId);
        // 주문 상태가 '미결제' 나 '결제완료' 상태가 아니면 예외처리
        if(!order.getStatus().equals(OrderStatusType.BEFORE_PAYMENT.getStatus())
                && !order.getStatus().equals(OrderStatusType.AFTER_PAYMENT.getStatus())){
            throw new OrderException(OrderExceptionType.CANNOT_CANCEL_ORDER);
        }

        // orders 테이블에서 status 수정
        orderDao.updateOrderStatus(orderId, OrderStatusType.CANCEL.getStatus());
        // order_status_log 테이블에도 주문취소 로그 추가
        OrderStatusLog orderStatusLog = new OrderStatusLog(OrderStatusType.CANCEL.getStatus(), userId, orderId);
        orderDao.addOrderStatusLog(orderStatusLog);
    }




}
