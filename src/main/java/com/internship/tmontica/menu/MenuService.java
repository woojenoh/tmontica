package com.internship.tmontica.menu;

import com.internship.tmontica.menu.exception.MenuException;
import com.internship.tmontica.menu.exception.MenuExceptionType;
import com.internship.tmontica.menu.model.response.MenuDetailResponse;
import com.internship.tmontica.menu.model.response.MenuMainResponse;
import com.internship.tmontica.menu.model.response.MenuOptionResponse;
import com.internship.tmontica.menu.model.response.MenuSimpleResponse;
import com.internship.tmontica.option.Option;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuDao menuDao;

    private final ModelMapper modelMapper;

    @Value("${menu.imagepath}")
    private String location;

    // 메인 화면에 나타나는 메뉴 정보 (이달의 메뉴, 카테고리 별 메뉴)
    public List<MenuMainResponse> getMainMenus(){

        List<MenuMainResponse> allMenus = new ArrayList<>();
        allMenus.add(getMonthlyMainMenu(4));
        Arrays.stream(CategoryName.values())
                .forEach(categoryName -> allMenus.add(getMainMenu(categoryName.getCategoryEng(), 8)));

        return allMenus;
    }

    // 하나의 메뉴 상세 정보 가져오기
    public MenuDetailResponse getMenuDetailById(int id){

        Menu menu = MenuManager.getMenuById(id);
        List<Option> options = menuDao.getOptionsById(id);

        MenuDetailResponse menuDetailResponse = modelMapper.map(menu, MenuDetailResponse.class);
        List<MenuOptionResponse> menuOptions = modelMapper.map(options, new TypeToken<List<MenuOptionResponse>>(){}.getType());

        menuDetailResponse.setOption(menuOptions);

        return menuDetailResponse;
    }

    // 카테고리 별 메뉴 정보 가져오기
    public List<MenuSimpleResponse> getMenusByCategory(String category, int page, int size){

        validateCategoryName(category);
        List<Menu> usableMenus = MenuManager.getUsableMenusWithCheckEmpty();
        List<Menu> menus = usableMenus.stream()
                .filter(menu -> menu.isSameCategory(category))
                .collect(Collectors.toList());

        List<Menu> menusByPage = getMenusByPage(page, size, menus);
        if(menusByPage.isEmpty()){
            throw new MenuException(MenuExceptionType.MENU_NO_CONTENT_EXCEPTION);
        }

        return modelMapper.map(menus, new TypeToken<List<MenuSimpleResponse>>(){}.getType());
    }

    // 메뉴 정보 가져오기 (전체)
    public List<Menu> getAllMenus(int page, int size){

        List<Menu> usableMenus = MenuManager.getUsableMenusWithCheckEmpty();
        return getMenusByPage(page, size, usableMenus);
    }

    private List<Menu> getMenusByPage(int page, int size, List<Menu> menus) {

        int startIndex = (page - 1) * size;

        if(startIndex < 0 || startIndex >= menus.size()){
            return new ArrayList<>();
        }

        int endIndex = (startIndex + size < menus.size())? startIndex + size : menus.size();
        return menus.subList(startIndex, endIndex);
    }


    // 메인 화면에 필요한 카테고리 별 메뉴 정보 가져오기.
    private MenuMainResponse getMainMenu(String categoryEng, int contentsNumber){

        MenuMainResponse menuMainResponse = new MenuMainResponse(categoryEng, CategoryName.convertEngToKo(categoryEng));
        List<Menu> usableMenus = MenuManager.getUsableMenus();

        if(usableMenus.isEmpty()){
            menuMainResponse.setMenus(new ArrayList<>());
            return menuMainResponse;
        }

        List<Menu> menus = usableMenus.stream()
                              .filter(menu -> menu.isSameCategory(categoryEng))
                              .limit(contentsNumber)
                              .collect(Collectors.toList());

        List<MenuSimpleResponse> menuSimpleList = modelMapper.map(menus, new TypeToken<List<MenuSimpleResponse>>(){}.getType());
        menuMainResponse.setMenus(menuSimpleList);
        return menuMainResponse;
    }

    // 메인 화면의 이달의 메뉴 가져오기
    private MenuMainResponse getMonthlyMainMenu(int contentsNumber){

        MenuMainResponse menuMainResponse = new MenuMainResponse("monthlymenu", "이달의 메뉴");
        List<Menu> usableMenus = MenuManager.getUsableMenus();

        if(usableMenus.isEmpty()){
            menuMainResponse.setMenus(new ArrayList<>());
            return menuMainResponse;
        }

        List<Menu> menus = usableMenus.stream()
                                .filter(Menu::isMonthlyMenu)
                                .limit(contentsNumber)
                                .collect(Collectors.toList());

        List<MenuSimpleResponse> menuSimpleList = modelMapper.map(menus, new TypeToken<List<MenuSimpleResponse>>(){}.getType());

        menuMainResponse.setMenus(menuSimpleList);
        return menuMainResponse;
    }

    // 카테고리 이름 체크
    private void validateCategoryName(String categoryName){

        for(CategoryName element : CategoryName.values()){
            if(element.getCategoryEng().equals(categoryName)){
                return;
            }
        }
        throw new MenuException(MenuExceptionType.CATEGORY_NAME_MISMATCH_EXCEPTION);
    }
}
