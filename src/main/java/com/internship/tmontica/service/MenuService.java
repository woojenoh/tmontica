package com.internship.tmontica.service;

import com.internship.tmontica.dto.Menu;
import com.internship.tmontica.dto.Option;
import com.internship.tmontica.repository.MenuDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuDao menuDao;

    // TODO : 예외처리..

    // 메뉴 추가
    @Transactional
    public int addMenu(Menu menu){
        int menuId =  menuDao.addMenu(menu);
        for(Option option : menu.getOptions())
            menuDao.addMenuOption(menuId , option.getId());
        return menuId;
    }

    // 메뉴 옵션 추가
    @Transactional
    public int addMenuOption(int menuId, int optionId){
        return menuDao.addMenuOption(menuId, optionId);
    }

    // 하나의 메뉴 정보 가져오기
    @Transactional(readOnly = true)
    public Menu getMenuById(int id){
        return menuDao.getMenuById(id);
    }

    // 메뉴의 옵션 정보 가져오기
    @Transactional(readOnly = true)
    public List<Option> getOptionsById(int id){
        return menuDao.getOptionsById(id);
    }

    // 모든 메뉴 정보 가져오기
    @Transactional(readOnly = true)
    public List<Menu> getAllMenus(){
        return menuDao.getAllMenus();
    }

    // 카테고리 별 메뉴 정보 가져오기
    @Transactional(readOnly = true)
    public List<Menu> getMenusByCategory(String category){
        return menuDao.getMenusByCategory(category);
    }

    // 이달의 메뉴 정보 가져오기
    @Transactional(readOnly = true)
    public List<Menu> getMonthlyMenus(){
        return menuDao.getMonthlyMenus();
    }

    // 메뉴 수정하기
    @Transactional
    public void updateMenu(Menu menu){
        menuDao.updateMenu(menu);
    }

    // 메뉴 삭제하기
    @Transactional
    public void deleteMenu(int id){
        menuDao.deleteMenu(id);
    }
}
