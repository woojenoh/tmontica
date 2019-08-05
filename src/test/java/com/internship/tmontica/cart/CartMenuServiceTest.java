package com.internship.tmontica.cart;

import com.internship.tmontica.cart.exception.CartUserException;
import com.internship.tmontica.cart.model.request.CartUpdateReq;
import com.internship.tmontica.cart.model.response.CartResp;
import com.internship.tmontica.menu.Menu;
import com.internship.tmontica.menu.MenuDao;
import com.internship.tmontica.option.Option;
import com.internship.tmontica.option.OptionDao;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

//        cartMenu = new CartMenu();
//        cartMenu.setId(2);
//        cartMenu.setMenuId(3);
//        cartMenu.setOption("");
//        cartMenu.setPrice(2100);
//        cartMenu.setQuantity(3);
//        cartMenu.setDirect(false);
//        cartMenu.setUserId("testid");

    }

    @Test
    public void 카트정보_가져오기() {
        // given
        List<CartMenu> cartMenus = new ArrayList<>();
        cartMenus.add(cartMenu);
        cartMenus.
        Menu menu = new Menu(2, "latte", 2000, "커피", "coffee",
                false, true, "asdfa/asdfa.png", "맛있는 라떼", 1500,
                10,new Date(),new Date() , "admin", "admin",100, "라떼",new Date() ,new Date() );

        Mockito.when(jwtService.getUserInfo("userInfo")).thenReturn("{\"id\":\"testid\"}");
        Mockito.when(cartMenuDao.getCartMenuByUserId("testid")).thenReturn(cartMenus);
        DB옵션문자열변환();
        Mockito.when(menuDao.getMenuById(cartMenu.getMenuId())).thenReturn(menu);

        // when
        CartResp cartResp = cartMenuService.getCartMenuApi();
        System.out.println(cartResp);

        // then

    }

    @Test
    public void addCartApi() {

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
        Mockito.when(optionDao.getOptionById(4)).thenReturn(new Option("SizeUp", 500, "Size"));

        // when
        String str = cartMenuService.convertOptionStringToCli("1__1/3__2/4__1");
        System.out.println(str);

    }
}