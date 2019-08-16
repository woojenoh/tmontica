package com.internship.tmontica.menu;


import com.internship.tmontica.menu.exception.MenuException;
import com.internship.tmontica.menu.model.response.MenuDetailResponse;
import com.internship.tmontica.menu.model.response.MenuOptionResponse;
import com.internship.tmontica.menu.model.response.MenuSimpleResponse;
import com.internship.tmontica.option.Option;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.modelmapper.ModelMapper;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;


@RunWith(PowerMockRunner.class)
@PrepareForTest(MenuManager.class)
@Import(TestConfiguration.class)
public class MenuServiceTest {
    @Mock
    MenuDao menuDao;
//
//    ModelMapper modelMapper;

    @InjectMocks
    MenuService menuService;

    private List<Menu> usableMenus = new ArrayList<>();

    @Before
    public void setUp(){
        Menu menu1 = Menu.builder().id(1).categoryEng("coffee").categoryKo("커피").usable(true)
                .monthlyMenu(true).nameKo("콜드브루1").nameEng("Cold Brew1")
                .description("시원한 콜드브루").discountRate(10).productPrice(2000)
                .sellPrice(1800).stock(100).createdDate(new Date()).build();

        Menu menu2 = Menu.builder().id(2).categoryEng("coffee").categoryKo("커피").usable(true)
                .monthlyMenu(true).nameKo("콜드브루2").nameEng("Cold Brew2")
                .description("시원한 콜드브루").discountRate(10).productPrice(2000)
                .sellPrice(1800).stock(100).createdDate(new Date()).build();

        Menu menu3 = Menu.builder().id(3).categoryEng("coffee").categoryKo("커피").usable(true)
                .monthlyMenu(true).nameKo("콜드브루3").nameEng("Cold Brew3")
                .description("시원한 콜드브루").discountRate(10).productPrice(2000)
                .sellPrice(1800).stock(100).createdDate(new Date()).build();

        Menu menu4 = Menu.builder().id(4).categoryEng("ade").categoryKo("에이드").usable(true)
                .monthlyMenu(true).nameKo("레몬에이드").nameEng("Lemonade")
                .description("상큼한 레몬에이드").discountRate(10).productPrice(2000)
                .sellPrice(1800).stock(100).createdDate(new Date()).build();

        Menu menu5 = Menu.builder().id(5).categoryEng("ade").categoryKo("에이드").usable(true)
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
        PowerMockito.mockStatic(MenuManager.class);
        given(MenuManager.getUsableMenusWithCheckEmpty()).willReturn(usableMenus);

        // when
        final List<Menu> menuList = menuService.getAllMenus(1,10);

        // then
        Assert.assertEquals(menuList.size(), usableMenus.size() > 9? 10 : usableMenus.size());
        Assert.assertThat(menuList.get(0), is(usableMenus.get(0)));

    }

    @Test
    public void 카테고리_메뉴_가져오기(){
        // given
        PowerMockito.mockStatic(MenuManager.class);
        given(MenuManager.getUsableMenusWithCheckEmpty()).willReturn(usableMenus);

        // when
        final List<MenuSimpleResponse> menuList = menuService.getMenusByCategory("ade", 1 , 10);

        // then
        Assert.assertEquals(menuList.size(), 2 );

    }

    @Test(expected = MenuException.class)
    public void 카테고리_메뉴_가져오기_예외(){
        // given
        PowerMockito.mockStatic(MenuManager.class);
        given(MenuManager.getUsableMenus()).willReturn(usableMenus);

        // when
        final List<MenuSimpleResponse> menuList = menuService.getMenusByCategory("ABC", 1 , 10);
    }

    @Test
    public void 하나의_메뉴_정보_가져오기(){
        //given
        Option hot = new Option("HOT", 0, "Temperature", 1);
        Option ice = new Option("ICE", 0, "Temperature", 1);
        List<Option> options = new ArrayList<>();
        options.add(hot);
        options.add(ice);

        List<MenuOptionResponse> optionResps = new ArrayList<>();

        PowerMockito.mockStatic(MenuManager.class);
        given(MenuManager.getMenuById(1)).willReturn(usableMenus.get(1));
        given(menuDao.getOptionsById(1)).willReturn(options);
//        given(modelMapper
//                .map(any(Menu.class), MenuDetailResponse.class)).willReturn(MenuDetailResponse.builder().id(1).nameKo(usableMenus.get(1).getNameKo()).build());
//        given(modelMapper
//                .map(,new TypeToken<List<MenuOptionResponse>>(){}.getType())).willReturn(optionResps);



        //when
       MenuDetailResponse menu = menuService.getMenuDetailById(1);

        //then
        Assert.assertEquals(menu.getNameKo(), usableMenus.get(1).getNameKo());

    }

}