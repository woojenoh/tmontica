package com.internship.tmontica.menu;

import com.internship.tmontica.menu.exception.MenuException;
import com.internship.tmontica.menu.exception.MenuExceptionType;
import com.internship.tmontica.menu.model.response.MenuCategoryResp;
import com.internship.tmontica.menu.model.response.MenuDetailResp;
import com.internship.tmontica.menu.model.response.MenuMainResp;
import com.internship.tmontica.menu.model.response.MenuSimpleResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    private final ModelMapper modelMapper;

    @Value("${menu.imagepath}")
    private String location;


    /** 전체 메뉴 (메인 화면 ) **/
    @GetMapping
    public ResponseEntity<List<MenuMainResp>> getAllMenus(){
        List<MenuMainResp> allMenus = menuService.getMainMenus();
        return new ResponseEntity<>(allMenus, HttpStatus.OK);
    }

    /** 전체 메뉴 가져오기 **/
    @GetMapping("/all")
    public ResponseEntity<List<Menu>> getAllMenus(@RequestParam(value = "page", required = false, defaultValue = "1")int page,
                                                  @RequestParam(value = "size", required = false, defaultValue = "10")int size){

        List<Menu> menus = menuService.getAllMenus(page, size);
        if (menus.isEmpty()) {
            throw new MenuException(MenuExceptionType.MENU_NO_CONTENT_EXCEPTION);
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);

    }

    /** 카테고리 별 메뉴 가져오기 **/
    @GetMapping("/{category:[a-z-]+}")
    public ResponseEntity<MenuCategoryResp> getMenusByCategory(@PathVariable("category")String category,
                                                               @RequestParam(value = "page", required = false) int page,
                                                               @RequestParam(value = "size", required = false) int size){

        MenuCategoryResp menucategoryResp = new MenuCategoryResp();
        menucategoryResp.setSize(size);
        menucategoryResp.setPage(page);
        menucategoryResp.setCategoryEng(category);
        menucategoryResp.setCategoryKo(CategoryName.convertEngToKo(category));

        List<Menu> menus = menuService.getMenusByCategory(category, page, size);
        // 메뉴가 없는 경우
        if(menus == null) {
            throw new MenuException(MenuExceptionType.MENU_NO_CONTENT_EXCEPTION);
        }
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
        if(menuDetailResp == null) {
            throw new MenuException(MenuExceptionType.MENU_NO_CONTENT_EXCEPTION);
        }
        return new ResponseEntity<>(menuDetailResp, HttpStatus.OK);
    }

}
