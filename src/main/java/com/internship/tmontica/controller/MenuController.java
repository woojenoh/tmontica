package com.internship.tmontica.controller;

import com.internship.tmontica.dto.Menu;
import com.internship.tmontica.dto.response.*;
import com.internship.tmontica.service.MenuService;
import com.internship.tmontica.util.CategoryName;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private ModelMapper modelMapper;

    /** 전체 메뉴 (메인 화면 ) **/
    @GetMapping
    public ResponseEntity<List<MenuCategoryResp>> getAllMenus(){
        List<MenuCategoryResp> allMenus = new ArrayList<>();

        // 이달의 메뉴
        MenuCategoryResp menuMonthly = new MenuCategoryResp();
        menuMonthly.setCategoryKo("이달의 메뉴");
        menuMonthly.setCategoryEng("monthly menu");
        // DB에서 이달의 메뉴 정보를 가져옴
        List<Menu> montlyMenus = menuService.getMonthlyMenus();
        // Menu --> MenuSimpleResp
        Type listType = new TypeToken<List<MenuSimpleResp>>(){}.getType();
        List<MenuSimpleResp> monthlyMenuList = modelMapper.map(montlyMenus, listType);
        menuMonthly.setMenus(monthlyMenuList);

        allMenus.add(menuMonthly);

        // 카테고리 : coffee
        MenuCategoryResp menuCoffee = new MenuCategoryResp();
        menuCoffee.setCategoryKo(CategoryName.CATEGORY_COFFEE_KO);
        menuCoffee.setCategoryEng(CategoryName.CATEGORY_COFFEE);

        // DB에서 커피 카테고리 메뉴 정보를 가져옴
        List<Menu> coffeeMenus = menuService.getMenusByCategory(CategoryName.CATEGORY_COFFEE);

        // Menu --> MenuSimpleResp
        List<MenuSimpleResp> coffeeMenuList = modelMapper.map(coffeeMenus, listType);
        menuCoffee.setMenus(coffeeMenuList);

        allMenus.add(menuCoffee);

        // 카테고리 : ade
        MenuCategoryResp menuAde = new MenuCategoryResp();
        menuAde.setCategoryKo(CategoryName.CATEGORY_ADE_KO);
        menuAde.setCategoryEng(CategoryName.CATEGORY_ADE);

        //  DB에서 에이드 카테고리의 메뉴 정보를 가져옴
        List<Menu> adeMenus = menuService.getMenusByCategory(CategoryName.CATEGORY_ADE);

        // Menu --> MenuSimpleResp
        List<MenuSimpleResp> adeMenuList = modelMapper.map(menuAde, listType);
        menuAde.setMenus(adeMenuList);

        allMenus.add(menuAde);

        return new ResponseEntity<>(allMenus, HttpStatus.OK);
    }

    /** 카테고리 별 메뉴 가져오기 **/
    @GetMapping("/{category}")
    public ResponseEntity<MenuCateResp> getMenusByCategory(@PathVariable("category")String category){
        // mock 데이터
        MenuCateResp menuResp = new MenuCateResp();
        menuResp.setSize(2);
        List<MenuSimpleResp> menus = new ArrayList<>();
        menus.add(new MenuSimpleResp(1, "americano", "아메리카노" , "/img/coffee/americano"));
        menus.add(new MenuSimpleResp(2, "americano", "아메리카노" , "/img/coffee/americano"));
        menuResp.setMenus(menus);
        return new ResponseEntity<>(menuResp, HttpStatus.OK);
    }

    /** 상세 메뉴 정보 가져오기 **/
    @GetMapping("/{categoryEng}/{menuId}")
    public MenuDetailResp getMenuDetail(@PathVariable("categoryEng")String categoryEng, @PathVariable("menuId")int menuId){
        //더미 데이터
        List options = new ArrayList<>();
        options.add(new OptionsResp(1,"Temperature","HOT",0));
        options.add(new OptionsResp(3,"Size","SizeUp",500));
        options.add(new OptionsResp(4,"Shot","AddShot",300));
        MenuDetailResp menuDetailResp =
                new MenuDetailResp(1,"americano","아메리카노","산미가 풍부한 아메리카노","asdfad",1000,10,"coffee","커피",100,false,options);
        return menuDetailResp;
    }

}
