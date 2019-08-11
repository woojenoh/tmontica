package com.internship.tmontica.menu;

import com.internship.tmontica.menu.exception.MenuException;
import com.internship.tmontica.menu.exception.MenuExceptionType;
import com.internship.tmontica.menu.model.response.MenuDetailResp;
import com.internship.tmontica.menu.model.response.MenuMainResp;
import com.internship.tmontica.menu.model.response.MenuOptionResp;
import com.internship.tmontica.menu.model.response.MenuSimpleResp;
import com.internship.tmontica.option.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuDao menuDao;

    private final ModelMapper modelMapper;

    @Value("${menu.imagepath}")
    private String location;

    // 메인 화면에 나타나는 메뉴 정보
    public List<MenuMainResp> getMainMenus(){

        List<MenuMainResp> allMenus = new ArrayList<>();
        // 이달의 메뉴 , 커피 , 에이드 , 빵
        for(CategoryName categoryName : CategoryName.values()){
            allMenus.add(getMenuMainResp(categoryName.getCategoryEng()));
        }

        return allMenus;
    }

    // 하나의 메뉴 상세 정보 가져오기
    public MenuDetailResp getMenuDetailById(int id){
        Menu menu = null;
        List<Menu> usableMenus = MenuScheduler.getUsableMenus();
        // 사용가능한 메뉴가 존재하지 않을 경우
        if(usableMenus.isEmpty()){
            throw new MenuException(MenuExceptionType.MENU_NO_CONTENT_EXCEPTION);
        }

        for(Menu checkMenu : usableMenus){
            if(checkMenu.getId() == id){
                menu = checkMenu;
                break;
            }
        }

        if(menu == null){
            throw new MenuException(MenuExceptionType.MENU_NO_CONTENT_EXCEPTION);
        }

        // 메뉴의 옵션 정보 가져오기
        List<Option> options = menuDao.getOptionsById(id);

        MenuDetailResp menuDetailResp = modelMapper.map(menu, MenuDetailResp.class);
        List<MenuOptionResp> menuOptions = modelMapper.map(options, new TypeToken<List<MenuOptionResp>>(){}.getType());

        menuDetailResp.setOption(menuOptions);

        return menuDetailResp;
    }

    // 카테고리 별 메뉴 정보 가져오기
    public List<Menu> getMenusByCategory(String category, int page, int size){
        // 카테고리 이름 체크
        checkCategoryName(category);

        List<Menu> usableMenus = MenuScheduler.getUsableMenus();
        // 사용가능한 메뉴가 존재하지 않을 경우
        if(usableMenus.isEmpty()){
            throw new MenuException(MenuExceptionType.MENU_NO_CONTENT_EXCEPTION);
        }
        // 해당 카테고리의 모든 메뉴를 가져온다.
        List<Menu> categoryMenus = usableMenus.stream().filter(menu -> menu.getCategoryEng().equals(category))
                                              .collect(Collectors.toList());
        // 가져온 메뉴들 중 페이지에 맞는 메뉴들만 리턴한다.
        return getMenusByPage(page, size, categoryMenus);

    }

    // 메뉴 정보 가져오기 (전체)
    public List<Menu> getAllMenus(int page, int size){
        // 페이지에 맞는 메뉴들만 리턴한다.
        List<Menu> usableMenus = MenuScheduler.getUsableMenus();
        // 사용가능한 메뉴가 존재하지 않을 경우
        if(usableMenus.isEmpty()){
            throw new MenuException(MenuExceptionType.MENU_NO_CONTENT_EXCEPTION);
        }
        return getMenusByPage(page, size, usableMenus);

    }

    private List<Menu> getMenusByPage(int page, int size, List<Menu> menus) {
        int startIndex = (page - 1) * size;

        if(startIndex < 0 || startIndex >= menus.size())
            return new ArrayList<>();

        int endIndex = (startIndex + size < menus.size())? startIndex + size : menus.size();

        return menus.subList(startIndex, endIndex);
    }


    // 메인 화면에 필요한 메뉴정보 가져오기.
    private MenuMainResp getMenuMainResp(String categoryEng){
        // 메인 화면에 나타나는 메뉴 정보
        List<Menu> usableMenus = MenuScheduler.getUsableMenus();
        MenuMainResp menuMainResp = new MenuMainResp();
        menuMainResp.setCategoryEng(categoryEng);
        menuMainResp.setCategoryKo(CategoryName.convertEngToKo(categoryEng));
        List<Menu> menus = new ArrayList<>();

        int count = 0;

        if(usableMenus.isEmpty()){
            menuMainResp.setMenus(new ArrayList<>());
            return menuMainResp;
        }

        if(categoryEng.equals(CategoryName.CATEGORY_MONTHLY.getCategoryEng())){  // 4개 까지 가져온다.
            for(Menu menu : usableMenus){
                if(count == 4) break;
                if(menu.isMonthlyMenu()){
                    menus.add(menu);
                    count++;
                }

            }
        }else{
            for(Menu menu : usableMenus){   // 8개 까지 가져온다.
                if(count == 8) break;
                if(menu.getCategoryEng().equals(categoryEng)){
                    menus.add(menu);
                    count++;
                }
            }
        }

        List<MenuSimpleResp> menuSimpleList = modelMapper.map(menus, new TypeToken<List<MenuSimpleResp>>(){}.getType());

        menuMainResp.setMenus(menuSimpleList);
        return menuMainResp;
    }

    // 카테고리 이름 체크
    public void checkCategoryName(String categoryName){

        for(CategoryName element : CategoryName.values()){
            if(element.getCategoryEng().equals(categoryName)){
                return;
            }
        }

        throw new MenuException(MenuExceptionType.CATEGORY_NAME_MISMATCH_EXCEPTION);
    }
}
