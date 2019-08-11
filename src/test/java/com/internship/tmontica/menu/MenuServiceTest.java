package com.internship.tmontica.menu;


import com.internship.tmontica.menu.exception.MenuException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;


@RunWith(PowerMockRunner.class)
@PrepareForTest(MenuScheduler.class)
public class MenuServiceTest {
    @Mock
    MenuDao menuDao;

    @InjectMocks
    MenuService menuService;

    private List<Menu> usableMenus = new ArrayList<>();

    @Before
    public void setUp(){
        Menu menu1 = Menu.builder().categoryEng("coffee").categoryKo("커피").usable(true)
                .monthlyMenu(true).nameKo("콜드브루1").nameEng("Cold Brew1")
                .description("시원한 콜드브루").discountRate(10).productPrice(2000)
                .sellPrice(1800).stock(100).createdDate(new Date()).build();

        Menu menu2 = Menu.builder().categoryEng("coffee").categoryKo("커피").usable(true)
                .monthlyMenu(true).nameKo("콜드브루2").nameEng("Cold Brew2")
                .description("시원한 콜드브루").discountRate(10).productPrice(2000)
                .sellPrice(1800).stock(100).createdDate(new Date()).build();

        Menu menu3 = Menu.builder().categoryEng("coffee").categoryKo("커피").usable(true)
                .monthlyMenu(true).nameKo("콜드브루3").nameEng("Cold Brew3")
                .description("시원한 콜드브루").discountRate(10).productPrice(2000)
                .sellPrice(1800).stock(100).createdDate(new Date()).build();

        Menu menu4 = Menu.builder().categoryEng("ade").categoryKo("에이드").usable(true)
                .monthlyMenu(true).nameKo("레몬에이드").nameEng("Lemonade")
                .description("상큼한 레몬에이드").discountRate(10).productPrice(2000)
                .sellPrice(1800).stock(100).createdDate(new Date()).build();

        Menu menu5 = Menu.builder().categoryEng("ade").categoryKo("에이드").usable(true)
                .monthlyMenu(true).nameKo("블루레몬에이드").nameEng("Blue Lemonade")
                .description("시원한 블루레몬에이드").discountRate(10).productPrice(2000)
                .sellPrice(1800).stock(100).createdDate(new Date()).build();

        usableMenus = new ArrayList<>();

        usableMenus.add(menu1);
        usableMenus.add(menu2);
        usableMenus.add(menu3);
        usableMenus.add(menu4);
        usableMenus.add(menu5);

    }

    @Test
    public void 전체_메뉴_가져오기(){
        //  given
        PowerMockito.mockStatic(MenuScheduler.class);
        given(MenuScheduler.getUsableMenus()).willReturn(usableMenus);

        // when
        final List<Menu> menuList = menuService.getAllMenus(1,10);

        // then
        Assert.assertEquals(menuList.size(), usableMenus.size() > 9? 10 : usableMenus.size());
        Assert.assertThat(menuList.get(0), is(usableMenus.get(0)));

    }

    @Test
    public void 카테고리_메뉴_가져오기(){
        // given
        PowerMockito.mockStatic(MenuScheduler.class);
        given(MenuScheduler.getUsableMenus()).willReturn(usableMenus);

        // when
        final List<Menu> menuList = menuService.getMenusByCategory("ade", 1 , 10);

        // then
        Assert.assertEquals(menuList.size(), 2 );

    }

    @Test(expected = MenuException.class)
    public void 카테고리_메뉴_가져오기_예외(){
        // given
        PowerMockito.mockStatic(MenuScheduler.class);
        given(MenuScheduler.getUsableMenus()).willReturn(usableMenus);

        // when
        final List<Menu> menuList = menuService.getMenusByCategory("ABC", 1 , 10);
    }

//    @Test()

}