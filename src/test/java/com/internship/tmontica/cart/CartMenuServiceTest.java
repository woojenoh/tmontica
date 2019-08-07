package com.internship.tmontica.cart;

import com.internship.tmontica.cart.exception.CartUserException;
import com.internship.tmontica.cart.model.request.CartReq;
import com.internship.tmontica.cart.model.request.CartUpdateReq;
import com.internship.tmontica.cart.model.request.Cart_OptionReq;
import com.internship.tmontica.cart.model.response.CartIdResp;
import com.internship.tmontica.cart.model.response.CartResp;
import com.internship.tmontica.menu.Menu;
import com.internship.tmontica.menu.MenuDao;
import com.internship.tmontica.option.Option;
import com.internship.tmontica.option.OptionDao;
import com.internship.tmontica.order.exception.NotEnoughStockException;
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

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CartMenuServiceTest {

    @InjectMocks
    private CartMenuService cartMenuService;

    @Mock
    private CartMenuDao cartMenuDao;
    @Mock
    private OptionDao optionDao;
    @Mock
    private MenuDao menuDao;
    @Mock
    private JwtService jwtService;
    @Mock
    private CartMenu cartMenu;
    @Mock
    private CartMenu cartMenu2;
    @Mock
    private Menu menu;


    @Before
    public void setUp() throws Exception {
        cartMenu = new CartMenu();
        cartMenu.setId(1);
        cartMenu.setMenuId(2);
        cartMenu.setOption("1__1/3__2");
        cartMenu.setPrice(600);
        cartMenu.setQuantity(2);
        cartMenu.setDirect(false);
        cartMenu.setUserId("testid");

        cartMenu2 = new CartMenu();
        cartMenu2.setId(2);
        cartMenu2.setMenuId(2);
        cartMenu2.setOption("");
        cartMenu2.setPrice(600);
        cartMenu2.setQuantity(3);
        cartMenu2.setDirect(false);
        cartMenu2.setUserId("testid");

        menu = new Menu(2, "latte", 2000, "커피", "coffee",
                false, true, "asdfa/asdfa.png", "맛있는 라떼", 1500,
                10,new Date(),new Date() , "admin", "admin",100, "라떼",
                new Date() ,new Date(), false );


    }

    @Test
    public void 카트정보_가져오기() {
        // given
        List<CartMenu> cartMenus = new ArrayList<>();
        cartMenus.add(cartMenu);
        cartMenus.add(cartMenu2);

        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        Mockito.when(cartMenuDao.getCartMenuByUserId("testid")).thenReturn(cartMenus);
        DB옵션문자열변환();
        Mockito.when(menuDao.getMenuById(cartMenu.getMenuId())).thenReturn(menu);

        // when
        CartResp cartResp = cartMenuService.getCartMenuApi();
        System.out.println(cartResp);

        // then
        assertEquals(cartResp.getSize(), cartMenu.getQuantity()+cartMenu2.getQuantity());
        assertEquals(cartResp.getMenus().size(), cartMenus.size());
        int totalPrice = 0;
        for (CartMenu cm: cartMenus) {
            totalPrice += (cm.getPrice()+menu.getSellPrice())*cm.getQuantity();
        }
        assertEquals(cartResp.getTotalPrice(), totalPrice);
    }



    @Test
    public void 카트추가하기() {
        // given
        List<CartReq> cartReqs = new ArrayList<>();
        List<Cart_OptionReq> option = new ArrayList<>();
        option.add(new Cart_OptionReq(1,1));
        option.add(new Cart_OptionReq(3,2));
        CartReq cartReq = new CartReq(cartMenu.getMenuId(),cartMenu.getQuantity(),option,false);
        cartReqs.add(cartReq);
        cartReqs.add(cartReq);
        DB옵션문자열변환();

        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        Mockito.when(menuDao.getMenuById(cartReq.getMenuId())).thenReturn(menu);

        // when
        List<CartIdResp> cartIds = cartMenuService.addCartApi(cartReqs);
        // 반환되는 카트 id 값들은 auto increment 된 pk 이기 때문에 테스트로 확인을 할 수가 없다...

        // then
        cartMenu.setId(0); // 추가할 때는 0으로 들어감
        Mockito.verify(cartMenuDao, Mockito.atLeastOnce()).addCartMenu(cartMenu);
    }

    @Test
    public void 카트추가하기_바로구매(){
        // given
        List<CartReq> cartReqs = new ArrayList<>();
        List<Cart_OptionReq> option = new ArrayList<>();
        option.add(new Cart_OptionReq(1,1));
        option.add(new Cart_OptionReq(3,2));
        CartReq cartReq = new CartReq(cartMenu.getMenuId(),cartMenu.getQuantity(),option,true);
        cartReqs.add(cartReq);
        DB옵션문자열변환();

        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        Mockito.when(menuDao.getMenuById(cartReq.getMenuId())).thenReturn(menu);

        // when
        cartMenuService.addCartApi(cartReqs);

        // then
        Mockito.verify(cartMenuDao, Mockito.times(1)).deleteDirectCartMenu("testid");
    }

    @Test(expected = NotEnoughStockException.class)
    public void 카트추가하기_재고부족(){
        // given
        List<CartReq> cartReqs = new ArrayList<>();
        List<Cart_OptionReq> option = new ArrayList<>();
        option.add(new Cart_OptionReq(1,1));
        option.add(new Cart_OptionReq(3,2));
        CartReq cartReq = new CartReq(2,200,option,true);
        cartReqs.add(cartReq);

        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        Mockito.when(menuDao.getMenuById(cartReq.getMenuId())).thenReturn(menu);

        // when
        cartMenuService.addCartApi(cartReqs);
    }


    @Test
    public void 카트_수정하기() {
        // given
        int id = cartMenu.getId();
        CartUpdateReq cartUpdateReq = new CartUpdateReq();
        cartUpdateReq.setQuantity(1);

        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        Mockito.when(cartMenuDao.getCartMenuByCartId(id)).thenReturn(cartMenu);

        // when
        cartMenuService.updateCartApi(id, cartUpdateReq);
        // then
        Mockito.verify(cartMenuDao, Mockito.times(1)).updateCartMenuQuantity(id, cartUpdateReq.getQuantity());
    }

    @Test(expected = CartUserException.class)
    public void 카트_수정하기_아이디불일치(){
        // given
        int id = cartMenu.getId();
        CartUpdateReq cartUpdateReq = new CartUpdateReq();
        cartUpdateReq.setQuantity(1);

        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"notvalidId\"}");
        Mockito.when(cartMenuDao.getCartMenuByCartId(id)).thenReturn(cartMenu);

        // when
        cartMenuService.updateCartApi(id, cartUpdateReq);
    }

    @Test
    public void 카트_삭제하기() {
        // given
        int id = cartMenu.getId();

        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        Mockito.when(cartMenuDao.getCartMenuByCartId(id)).thenReturn(cartMenu);

        // when
        cartMenuService.deleteCartApi(id);
        // then
        Mockito.verify(cartMenuDao, Mockito.times(1)).deleteCartMenu(id);
    }

    @Test(expected = CartUserException.class)
    public void 카트_삭제하기_아이디불일치(){
        // given
        int id = cartMenu.getId();

        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"notvalidId\"}");
        Mockito.when(cartMenuDao.getCartMenuByCartId(id)).thenReturn(cartMenu);

        // when
        cartMenuService.deleteCartApi(id);
    }

    @Test
    public void DB옵션문자열변환() {
        //given
        Mockito.when(optionDao.getOptionById(1)).thenReturn(new Option("HOT", 0, "Temperature"));
        Mockito.when(optionDao.getOptionById(2)).thenReturn(new Option("ICE", 0, "Temperature"));
        Mockito.when(optionDao.getOptionById(3)).thenReturn(new Option("AddShot", 300, "Shot"));
        Mockito.when(optionDao.getOptionById(4)).thenReturn(new Option("AddSyrup", 300, "Syrup"));
        Mockito.when(optionDao.getOptionById(5)).thenReturn(new Option("SizeUp", 500, "Size"));

        // when
        String str = cartMenuService.convertOptionStringToCli("1__1/3__2/4__1");
        assertEquals(str, "HOT/샷추가(2개)/시럽추가(1개)");

    }
}