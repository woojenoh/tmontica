package com.internship.tmontica.menu;

import com.internship.tmontica.menu.model.response.*;
import com.internship.tmontica.menu.model.request.MenuReq;
import com.internship.tmontica.menu.model.request.MenuUpdateReq;
import com.internship.tmontica.menu.validaton.MenuValidator;
import com.internship.tmontica.util.CategoryName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    private final ModelMapper modelMapper;

    private final MenuValidator menuValidator;

    @Value("${menu.imagepath}")
    private String location;

    /** 전체 메뉴 (메인 화면 ) **/
    @GetMapping
    public ResponseEntity<List<MenuMainResp>> getAllMenus(){
        List<MenuMainResp> allMenus = menuService.getMainMenus();
        return new ResponseEntity<>(allMenus, HttpStatus.OK);
    }

    /** 카테고리 별 메뉴 가져오기 **/
    @GetMapping("/{category:[a-z-]+}")
    public ResponseEntity<MenuCategoryResp> getMenusByCategory(@PathVariable("category")String category,
                                                               @RequestParam("page")int page,
                                                               @RequestParam("size")int size){

        MenuCategoryResp menucategoryResp = new MenuCategoryResp();
        menucategoryResp.setSize(size);
        menucategoryResp.setPage(page);
        menucategoryResp.setCategoryEng(category);
        menucategoryResp.setCategoryKo(CategoryName.categoryEngToKo(category));

        List<Menu> menus = menuService.getMenusByCategory(category, page, size);
        // 메뉴가 없으면 no content
        if(menus == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        List<MenuSimpleResp> categoryMenus = modelMapper.map(menus, new TypeToken<List<MenuSimpleResp>>(){}.getType());

        for(MenuSimpleResp menu : categoryMenus)
            menu.setImgUrl("/images/".concat(menu.getImgUrl()));

        menucategoryResp.setMenus(categoryMenus);
        return new ResponseEntity<>(menucategoryResp, HttpStatus.OK);
    }

    /** 상세 메뉴 정보 가져오기 **/
    @GetMapping("/{menuId:\\d+}")
    public ResponseEntity<MenuDetailResp> getMenuDetail(@PathVariable("menuId")int menuId){
        MenuDetailResp menuDetailResp = menuService.getMenuDetailById(menuId);
        // 메뉴가 없으면 no content
        if(menuDetailResp == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(menuDetailResp, HttpStatus.OK);
    }

    /** 메뉴 추가하기 **/
    @PostMapping
    public ResponseEntity addMenu(@ModelAttribute @Valid MenuReq menuReq,BindingResult bindingResult){
        //TODO : 예외 처리..
        if(bindingResult.hasErrors())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        menuValidator.validate(menuReq, bindingResult);
        if(bindingResult.hasErrors())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        log.info("[menu api] 메뉴 추가하기");
        log.info("menuReq : {}", menuReq.toString());

        Menu menu = new Menu();
        modelMapper.map(menuReq, menu);

        // 메뉴 저장
        menuService.addMenu(menu, menuReq.getOptionIds(), menuReq.getImgFile());
        return new ResponseEntity(HttpStatus.OK);
    }

    /** 메뉴 수정하기 **/
    @PutMapping
    public ResponseEntity updateMenu(@ModelAttribute @Valid MenuUpdateReq menuReq, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        log.info("[menu api] 메뉴 수정하기");
        log.info("menuReq : {}", menuReq.toString());

        Menu menu = new Menu();
        menu.setId(menuReq.getMenuId());
        modelMapper.map(menuReq, menu);

        menuService.updateMenu(menu, menuReq.getImgFile());

        return new ResponseEntity(HttpStatus.OK);
    }

    /** 메뉴 삭제하기 **/
    @DeleteMapping("/{menuId}")
    public ResponseEntity deleteMenu(@PathVariable("menuId")int menuId ){
        log.info("[menu api] 메뉴 삭제하기");
        log.info("menuId : {}", menuId);

        menuService.deleteMenu(menuId);
        return new ResponseEntity(HttpStatus.OK);
    }



}
