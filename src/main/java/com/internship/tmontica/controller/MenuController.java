package com.internship.tmontica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.tmontica.dto.Menu;
import com.internship.tmontica.dto.Option;
import com.internship.tmontica.dto.request.MenuReq;
import com.internship.tmontica.dto.response.*;
import com.internship.tmontica.service.MenuService;
import com.internship.tmontica.util.CategoryName;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Slf4j
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
    @GetMapping("/category/{category}")
    public ResponseEntity<MenuCategoryResp> getMenusByCategory(@PathVariable("category")String category,
                                                               @RequestParam("page")int page,
                                                               @RequestParam("size")int size){

        MenuCategoryResp menucategoryResp = new MenuCategoryResp();
        menucategoryResp.setSize(size);
        menucategoryResp.setPage(page);
        menucategoryResp.setCategoryEng(category);
        menucategoryResp.setCategoryKo(CategoryName.categoryEngToKo(category));

        List<Menu> menus = menuService.getMenusByCategory(category, page, size);
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
        // 상세 메뉴 정보 가져오기
        Menu menu = menuService.getMenuById(menuId);
        // 메뉴의 옵션 정보 가져오기
        List<Option> options = menuService.getOptionsById(menuId);
        modelMapper.map(menu, menuDetailResp);
        menuDetailResp.setOption(options);

        return new ResponseEntity<>(menuDetailResp, HttpStatus.OK);
    }

    /** 메뉴 추가하기 **/
    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity addMenu(@ModelAttribute @Valid MenuReq menuReq, BindingResult bindingResult){

        //TODO : 로그인 유저 아이디 가져오기
        //TODO : 예외 처리..
        if(bindingResult.hasErrors())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        log.info("[menu api] 메뉴 추가하기");
        log.info("menuReq : {}", menuReq.toString());

        Menu menu = new Menu();
        menu.setCreatedDate(new Date());
        modelMapper.map(menuReq, menu);
        menu.setCreatorId(menuReq.getCreator());

        // 이미지 파일 저장
        String img = saveImg(menuReq.getImgFile(), menuReq.getCategoryEng(), menuReq.getNameEng());
        menu.setImgUrl(img);
        // 메뉴 저장
        menuService.addMenu(menu, menuReq.getOptionIds());
        return new ResponseEntity(HttpStatus.OK);

    }

    /** 메뉴 수정하기 **/

    /** 메뉴 삭제하기 **/
    @DeleteMapping("/{menuId}")
    public ResponseEntity deleteMenu(@PathVariable("menuId")int menuId){
        menuService.deleteMenu(menuId);
        return new ResponseEntity(HttpStatus.OK);
    }


    // 이미지 파일 저장
    private String saveImg(MultipartFile imgFile, String category, String name){
        // file url : imagefile/카테고리명/메뉴명
        String dir = "imagefile/";
        dir += category + "/";
        File dirFile = new File(dir);
        dirFile.mkdirs(); // 디렉토리가 없을 경우 만든다.
        dir += name;

        String extension = imgFile.getOriginalFilename().split("\\.")[1];
        dir += "." + extension;

        log.info("img type : {}", extension);

        try(FileOutputStream fos = new FileOutputStream(dir);
            InputStream in = imgFile.getInputStream()
        ){
            byte[] buffer = new byte[1024];
            int readCount = 0;
            while((readCount = in.read(buffer)) != -1){
                fos.write(buffer, 0, readCount);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return dir;
    }


}
