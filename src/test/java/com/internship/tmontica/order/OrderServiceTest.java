package com.internship.tmontica.order;

import com.internship.tmontica.cart.CartMenuDao;
import com.internship.tmontica.cart.CartMenuService;
import com.internship.tmontica.cart.CartMenuServiceTest;
import com.internship.tmontica.menu.MenuDao;
import com.internship.tmontica.order.exception.OrderUserException;
import com.internship.tmontica.order.model.response.OrderResp;
import com.internship.tmontica.order.model.response.Order_MenusResp;
import com.internship.tmontica.security.JwtService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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
    private JwtService jwtService;
    @Mock
    private CartMenuService cartMenuService;
    @Mock
    Order order1;
    Order order2;


    @Before
    public void setUp() throws Exception {
        order1 = new Order(1, new Date(),"현장결제", 3400, 1000, 2400, "미결제", "testid","");
        order2 = new Order(2, new Date(),"현장결제", 3000, 1500, 1500, "준비중", "testid","");

    }

    @Test
    public void 주문내역_여러개가져오기(){
        // given
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        Mockito.when(orderDao.getOrderByUserId("testid")).thenReturn(orders);
        List<String> menuNames = new ArrayList<>();
        menuNames.add("아메리카노");
        menuNames.add("카페라떼");
        Mockito.when(orderDao.getMenuNamesByOrderId(Mockito.anyInt())).thenReturn(menuNames);

        // when
        Map map = orderService.getOrderListApi();
        System.out.println(map.toString());

        // then
        List list = (List) map.get("orders");
        assertEquals(list.size(), orders.size());
    }


    @Test
    public void 주문정보_1개가져오기(){
        // given
        Mockito.when(orderDao.getOrderByOrderId(1)).thenReturn(order1);
        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        List<Order_MenusResp> menus = new ArrayList<>();
        menus.add(new Order_MenusResp(1, "americano", "아메리카노", "1__1/3__2/4__1", "adf/asdf.png", 1, 1900));
        menus.add(new Order_MenusResp(3, "bread", "빵", "", "adf/asdf.png", 1, 1500));
        Mockito.when(orderDao.getOrderDetailByOrderId(1)).thenReturn(menus);
        Mockito.when(cartMenuService.convertOptionStringToCli("1__1/3__2/4__1")).thenReturn("HOT/샷추가(2개)/시럽추가(1개)");

        // when
        OrderResp orderResp = orderService.getOneOrderApi(1);

        // then
        Mockito.verify(cartMenuService, Mockito.atLeastOnce()).convertOptionStringToCli(Mockito.anyString());
        System.out.println(orderResp);
    }

    @Test(expected = OrderUserException.class)
    public void 주문정보가져오기_아이디불일치(){
        // given
        Mockito.when(orderDao.getOrderByOrderId(1)).thenReturn(order1);
        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"invalidID\"}");

        // when
        orderService.getOneOrderApi(1);
    }

    @Test
    public void 주문취소(){
        // given
        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        Mockito.when(orderDao.getOrderByOrderId(1)).thenReturn(order1);


    }


}