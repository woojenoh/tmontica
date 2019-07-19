package com.internship.tmontica.controller;

import com.internship.tmontica.dto.Menu;
import com.internship.tmontica.dto.Option;
import com.internship.tmontica.dto.response.*;
import com.internship.tmontica.service.MenuService;
import com.internship.tmontica.util.CategoryName;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<MenuMainResp>> getAllMenus(){
        List<MenuMainResp> allMenus = new ArrayList<>();

        //1. 이달의 메뉴
        MenuMainResp menuMonthly = new MenuMainResp();
        menuMonthly.setCategoryKo("이달의 메뉴");
        menuMonthly.setCategoryEng("monthlymenu");
        // 1-1. DB에서 이달의 메뉴 정보를 가져옴
        List<Menu> montlyMenus = menuService.getMonthlyMenus();
        // 1-2. Menu --> MenuSimpleResp
        Type listType = new TypeToken<List<MenuSimpleResp>>(){}.getType();
        List<MenuSimpleResp> monthlyMenuList = modelMapper.map(montlyMenus, listType);
        menuMonthly.setMenus(monthlyMenuList);

        allMenus.add(menuMonthly);

        //2. 카테고리 : coffee
        MenuMainResp menuCoffee = new MenuMainResp();
        menuCoffee.setCategoryKo(CategoryName.CATEGORY_COFFEE_KO);
        menuCoffee.setCategoryEng(CategoryName.CATEGORY_COFFEE);

        // 2-1. DB에서 커피 카테고리 메뉴 정보를 가져옴
        List<Menu> coffeeMenus = menuService.getMenusByCategory(CategoryName.CATEGORY_COFFEE, 1, 8);

        // 2-2. Menu --> MenuSimpleResp
        List<MenuSimpleResp> coffeeMenuList = modelMapper.map(coffeeMenus, listType);
        menuCoffee.setMenus(coffeeMenuList);

        allMenus.add(menuCoffee);

        //3. 카테고리 : ade
        MenuMainResp menuAde = new MenuMainResp();
        menuAde.setCategoryKo(CategoryName.CATEGORY_ADE_KO);
        menuAde.setCategoryEng(CategoryName.CATEGORY_ADE);

        // 3-1. DB에서 에이드 카테고리의 메뉴 정보를 가져옴
        List<Menu> adeMenus = menuService.getMenusByCategory(CategoryName.CATEGORY_ADE, 1, 8);

        // 3-2. Menu --> MenuSimpleResp
        List<MenuSimpleResp> adeMenuList = modelMapper.map(adeMenus, listType);
        menuAde.setMenus(adeMenuList);

        allMenus.add(menuAde);

        return new ResponseEntity<>(allMenus, HttpStatus.OK);
    }

    /** 카테고리 별 메뉴 가져오기 **/
    @GetMapping("/{category}")
    public ResponseEntity<MenuCategoryResp> getMenusByCategory(@PathVariable("category")String category,
                                                           @RequestParam("page")int page,
                                                           @RequestParam("size")int size){

        MenuCategoryResp menucategoryResp = new MenuCategoryResp();
        menucategoryResp.setSize(size);
        menucategoryResp.setPage(page);
        menucategoryResp.setCategoryEng(category);
        menucategoryResp.setCategoryKo(CategoryName.categoryEngToKo(category));

        List<Menu> menus = menuService.getMenusByCategory(category , page, size);
        // TODO : 상태코드 어떻게 할지..
        if(menus == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        Type listType = new TypeToken<List<MenuSimpleResp>>(){}.getType();
        List<MenuSimpleResp> categoryMenus = modelMapper.map(menus, listType);
        menucategoryResp.setMenus(categoryMenus);
        return new ResponseEntity<>(menucategoryResp, HttpStatus.OK);
    }

    /** 상세 메뉴 정보 가져오기 **/
    @GetMapping("/{menuId}")
    public ResponseEntity<MenuDetailResp> getMenuDetail( @PathVariable("menuId")int menuId){
        MenuDetailResp menuDetailResp = new MenuDetailResp();

        Menu menu = menuService.getMenuById(menuId);

        List<Option> options = menuService.getOptionsById(menuId);
        modelMapper.map(menu , menuDetailResp);
        menuDetailResp.setOptions(options);

        return new ResponseEntity<>(menuDetailResp, HttpStatus.OK);
    }

}
