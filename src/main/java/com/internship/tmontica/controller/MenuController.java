package com.internship.tmontica.controller;


import com.internship.tmontica.dto.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    //@Autowired
    //private MenuService menuService;

    @GetMapping
    public ResponseEntity<List<MenuCategoryResp>> getAllMenus(){
        //  mock 데이터
        List<MenuCategoryResp> allMenus = new ArrayList<>();
        // 이달의 메뉴
        MenuCategoryResp menuMonthly = new MenuCategoryResp();
        menuMonthly.setCategoryKo("이달의 메뉴");
        menuMonthly.setCategoryEng("monthly menu");

        List<MenuSimpleResp> menus1 = new ArrayList<>();
        menus1.add(new MenuSimpleResp(1, "americano", "아메리카노" , "/img/coffee/americano"));
        menus1.add(new MenuSimpleResp(2, "lemonade", "레몬에이드" , "/img/ade/lemonade"));
        menus1.add(new MenuSimpleResp(3, "americano", "아메리카노" , "/img/coffee/americano"));
        menus1.add(new MenuSimpleResp(4, "americano", "아메리카노" , "/img/coffee/americano"));

        menuMonthly.setMenus(menus1);
        allMenus.add(menuMonthly);
        // 카테고리 : 커피
        MenuCategoryResp menuCoffee = new MenuCategoryResp();
        menuCoffee.setCategoryKo("커피");
        menuCoffee.setCategoryEng("coffee");

        List<MenuSimpleResp> menus2 = new ArrayList<>();
        menus2.add(new MenuSimpleResp(1, "americano", "아메리카노" , "/img/coffee/americano"));
        menus2.add(new MenuSimpleResp(4, "americano", "아메리카노" , "/img/coffee/americano"));
        menus2.add(new MenuSimpleResp(5, "americano", "아메리카노" , "/img/coffee/americano"));
        menus2.add(new MenuSimpleResp(6, "americano", "아메리카노" , "/img/coffee/americano"));
        menus2.add(new MenuSimpleResp(7, "americano", "아메리카노" , "/img/coffee/americano"));
        menus2.add(new MenuSimpleResp(8, "americano", "아메리카노" , "/img/coffee/americano"));
        menus2.add(new MenuSimpleResp(9, "americano", "아메리카노" , "/img/coffee/americano"));
        menus2.add(new MenuSimpleResp(10, "americano", "아메리카노" , "/img/coffee/americano"));
        menuCoffee.setMenus(menus2);
        allMenus.add(menuCoffee);

        return new ResponseEntity<>(allMenus, HttpStatus.OK);
    }


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

    @GetMapping("/{categoryEng}/{menuId}")
    public MenuDetailResp getMenuDetail(@PathVariable("categoryEng")String categoryEng, @PathVariable("menuId")int menuId){
        //더미 데이터
        List options = new ArrayList<>();
        options.add(new OptionsResp(1,"Temperature","HOT",0));
        options.add(new OptionsResp(3,"Size","SizeUp",500));
        options.add(new OptionsResp(4,"Shot","AddShot",300));
        MenuDetailResp menuDetailResp =
                new MenuDetailResp(1,"americano","아메리카노","산미가 풍부한 아메리카노","asdfad",1000,10,"coffee",100,false,options);
        return menuDetailResp;
    }
    

}
