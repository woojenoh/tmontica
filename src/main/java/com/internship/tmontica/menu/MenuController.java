package com.internship.tmontica.menu;

import com.internship.tmontica.menu.exception.MenuException;
import com.internship.tmontica.menu.exception.MenuExceptionType;
import com.internship.tmontica.menu.model.response.MenuCategoryResponse;
import com.internship.tmontica.menu.model.response.MenuDetailResponse;
import com.internship.tmontica.menu.model.response.MenuMainResponse;
import com.internship.tmontica.menu.model.response.MenuSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;


    /** 전체 메뉴 (메인 화면 ) **/
    @GetMapping
    public ResponseEntity<List<MenuMainResponse>> getAllMenus(){
        List<MenuMainResponse> allMenus = menuService.getMainMenus();
        return new ResponseEntity<>(allMenus, HttpStatus.OK);
    }

    /** 전체 메뉴 가져오기 **/
    @GetMapping("/all")
    public ResponseEntity<List<Menu>> getAllMenus(@RequestParam(value = "page", required = false, defaultValue = "1")int page,
                                                  @RequestParam(value = "size", required = false, defaultValue = "10")int size){

        List<Menu> menus = menuService.getAllMenus(page, size);
        return new ResponseEntity<>(menus, HttpStatus.OK);

    }

    /** 카테고리 별 메뉴 가져오기 **/
    @GetMapping("/{category:[a-z-]+}")  // front와 협의 후 변경하기.
    public ResponseEntity<MenuCategoryResponse> getMenusByCategory(@PathVariable("category")String category,
                                                                   @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                                   @RequestParam(value = "size", required = false, defaultValue = "10") int size){

        MenuCategoryResponse menucategoryResponse = new MenuCategoryResponse(size, page, category, CategoryName.convertEngToKo(category));
        List<MenuSimpleResponse> categoryMenus = menuService.getMenusByCategory(category, page, size);
        menucategoryResponse.setMenus(categoryMenus);
        return new ResponseEntity<>(menucategoryResponse, HttpStatus.OK);
    }

    /** 상세 메뉴 정보 가져오기 **/
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<MenuDetailResponse> getMenuDetail(@PathVariable("id")int id){
        MenuDetailResponse menuDetailResponse = menuService.getMenuDetailById(id);
        return new ResponseEntity<>(menuDetailResponse, HttpStatus.OK);
    }

}
