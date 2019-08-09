package com.internship.tmontica.order;

import com.internship.tmontica.cart.CartMenu;
import com.internship.tmontica.cart.CartMenuDao;
import com.internship.tmontica.cart.CartMenuService;
import com.internship.tmontica.cart.exception.CartException;
import com.internship.tmontica.menu.Menu;
import com.internship.tmontica.menu.MenuDao;
import com.internship.tmontica.order.exception.NotEnoughStockException;
import com.internship.tmontica.order.exception.OrderException;
import com.internship.tmontica.order.model.request.OrderReq;
import com.internship.tmontica.order.model.request.OrderMenusReq;
import com.internship.tmontica.order.model.response.OrderListByUserIdResp;
import com.internship.tmontica.order.model.response.OrderResp;
import com.internship.tmontica.order.model.response.OrderMenusResp;
import com.internship.tmontica.point.PointDao;
import com.internship.tmontica.point.exception.PointException;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.user.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mobile.device.Device;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderDao orderDao;
    @Mock
    private CartMenuDao cartMenuDao;
    @Mock
    private MenuDao menuDao;
    @Mock
    private UserDao userDao;
    @Mock
    private PointDao pointDao;
    @Mock
    private JwtService jwtService;
    @Mock
    private CartMenuService cartMenuService;
    @Mock
    private Device device;
    private Order order1;
    private Order order2;
    private Menu menu;


    @Before
    public void setUp() throws Exception {
        order1 = new Order(1, new Date(),"현장결제", 3400, 1000, 2400, "미결제", "testid","");
        order2 = new Order(2, new Date(),"현장결제", 3000, 0, 3000, "준비중", "testid","");

        menu = new Menu(2, "latte", 2000, "커피", "coffee",
                false, true, "asdfa/asdfa.png", "맛있는 라떼", 1500,
                10,new Date(),new Date() , "admin", "admin",100, "라떼",
                new Date() ,new Date(), false );
    }

    @Test
    public void 주문내역_여러개가져오기(){
        // given
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        when(orderDao.getOrderByUserId("testid", 0, 10)).thenReturn(orders);
        List<String> menuNames = new ArrayList<>();
        menuNames.add("아메리카노");
        menuNames.add("카페라떼");
        when(orderDao.getMenuNamesByOrderId(anyInt())).thenReturn(menuNames);

        // when
        OrderListByUserIdResp orderListByUserIdResp = orderService.getOrderListApi(1, 10);

        // then
        List list = orderListByUserIdResp.getOrders();
        assertTrue(list.size() <= 10);
    }


    @Test
    public void 결제하기_포인트사용(){
        // given
        when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        List<OrderMenusReq> menus = new ArrayList<>();
        menus.add(new OrderMenusReq(1));
        menus.add(new OrderMenusReq(2));
        OrderReq orderReq = new OrderReq(menus, 1000, 3600, "현장결제");

        when(userDao.getUserPointByUserId("testid")).thenReturn(3000);
        when(cartMenuDao.getCartMenuByCartId(anyInt())).thenReturn(new CartMenu(1, "2__1/3__1", "testid",300, 2, false));
        when(menuDao.getMenuById(anyInt())).thenReturn(menu);

        // when
        orderService.addOrderApi(orderReq, device);

        // then
        verify(userDao, times(1)).updateUserPoint(2000, "testid");
        verify(pointDao, times(1)).addPoint(any()); //Point
        verify(orderDao, times(1)).addOrder(any()); //Order
        verify(menuDao, atLeastOnce()).updateMenuStock(anyInt(), anyInt()); //menuId, stock-quantity
        verify(orderDao, atLeastOnce()).addOrderDetail(any());              //OrderDetail
        verify(cartMenuDao, atLeastOnce()).deleteCartMenu(anyInt());        //cartId
        verify(orderDao, times(1)).addOrderStatusLog(any()); //OrderStatusLog
    }


   @Test(expected = PointException.class)
    public void 결제하기_포인트익셉션(){
        // given
        when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        List<OrderMenusReq> menus = new ArrayList<>();
        menus.add(new OrderMenusReq(1));
        menus.add(new OrderMenusReq(2));
        OrderReq orderReq = new OrderReq(menus, 1000, 2600, "현장결제");

        when(userDao.getUserPointByUserId("testid")).thenReturn(900);

        // when
        orderService.addOrderApi(orderReq, device);
    }

    @Test(expected = CartException.class)
    public void 결제하기_아이디불일치(){
        // given
        when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"invalidID\"}");
        List<OrderMenusReq> menus = new ArrayList<>();
        menus.add(new OrderMenusReq(1));
        menus.add(new OrderMenusReq(2));
        OrderReq orderReq = new OrderReq(menus, 1000, 2600, "현장결제");

        when(userDao.getUserPointByUserId("invalidID")).thenReturn(3000);
        when(cartMenuDao.getCartMenuByCartId(anyInt())).thenReturn(new CartMenu(1, "2__1/3__1", "testid",1800, 2, false));

        // when
        orderService.addOrderApi(orderReq, device);
    }

    @Test(expected = NotEnoughStockException.class)
    public void 결제하기_재고부족(){
        // given
        when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        List<OrderMenusReq> menus = new ArrayList<>();
        menus.add(new OrderMenusReq(1));
        menus.add(new OrderMenusReq(2));
        OrderReq orderReq = new OrderReq(menus, 1000, 2600, "현장결제");

        when(userDao.getUserPointByUserId("testid")).thenReturn(3000);
        when(cartMenuDao.getCartMenuByCartId(anyInt())).thenReturn(new CartMenu(101, "2__1/3__1", "testid",1800, 2, false));
        when(menuDao.getMenuById(anyInt())).thenReturn(menu);

        // when
        orderService.addOrderApi(orderReq, device);
    }

    @Test(expected = OrderException.class)
    public void 결제하기_주문금액불일치(){
        // given
        when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        List<OrderMenusReq> menus = new ArrayList<>();
        menus.add(new OrderMenusReq(1));
        menus.add(new OrderMenusReq(2));
        OrderReq orderReq = new OrderReq(menus, 1000, 1500, "현장결제");

        when(userDao.getUserPointByUserId("testid")).thenReturn(3000);
        when(cartMenuDao.getCartMenuByCartId(anyInt())).thenReturn(new CartMenu(1, "2__1/3__1", "testid",300, 2, false));
        when(menuDao.getMenuById(anyInt())).thenReturn(menu);

        // when
        orderService.addOrderApi(orderReq, device);
    }

    @Test
    public void 주문정보_1개가져오기(){
        // given
        when(orderDao.getOrderByOrderId(1)).thenReturn(order1);
        when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        List<OrderMenusResp> menus = new ArrayList<>();
        menus.add(new OrderMenusResp(1, "americano", "아메리카노", "1__1/3__2/4__1", "adf/asdf.png", 1, 1900));
        menus.add(new OrderMenusResp(3, "bread", "빵", "", "adf/asdf.png", 1, 1500));
        when(orderDao.getOrderDetailByOrderId(1)).thenReturn(menus);
        when(cartMenuService.convertOptionStringToCli("1__1/3__2/4__1")).thenReturn("HOT/샷추가(2개)/시럽추가(1개)");

        // when
        OrderResp orderResp = orderService.getOneOrderApi(1);

        // then
        verify(cartMenuService, atLeastOnce()).convertOptionStringToCli(anyString());
        System.out.println(orderResp);
    }

    @Test(expected = OrderException.class)
    public void 주문정보1개가져오기_아이디불일치(){
        // given
        when(orderDao.getOrderByOrderId(1)).thenReturn(order1);
        when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"invalidID\"}");

        // when
        orderService.getOneOrderApi(1);
    }

    @Test
    public void 주문취소(){
        // given
        when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        when(orderDao.getOrderByOrderId(1)).thenReturn(order1);

        // when
        orderService.cancelOrderApi(1);

        // then
        verify(orderDao, times(1)).updateOrderStatus(1,OrderStatusType.CANCEL.getStatus());
        verify(orderDao, times(1)).addOrderStatusLog(new OrderStatusLog(OrderStatusType.CANCEL.getStatus(), "testid", 1));
    }


    @Test(expected = OrderException.class)
    public void 주문취소_아이디불일치(){
        // given
        when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"invalidID\"}");
        when(orderDao.getOrderByOrderId(1)).thenReturn(order1);

        // when
        orderService.cancelOrderApi(1);
    }

    @Test(expected = OrderException.class)
    public void 주문취소_취소불가능한상태(){
        // given
        when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        when(orderDao.getOrderByOrderId(2)).thenReturn(order2);

        // when
        orderService.cancelOrderApi(2);
    }

}