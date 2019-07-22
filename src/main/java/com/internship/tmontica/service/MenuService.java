package com.internship.tmontica.service;

import com.internship.tmontica.dto.Menu;
import com.internship.tmontica.dto.Option;
import com.internship.tmontica.repository.MenuDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuDao menuDao;

    // TODO : 예외처리..

    // 메뉴 추가
    @Transactional
    public int addMenu(Menu menu, List<Integer>optionIds){
        int menuId =  menuDao.addMenu(menu);
        System.out.println(menuId);
        for(int optionId : optionIds)
            menuDao.addMenuOption(menuId, optionId);
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
    public List<Menu> getMenusByCategory(String category, int page, int size){
        int offset = (page - 1) * size;
        return menuDao.getMenusByCategory(category, size, offset);
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

    // 수량 수정하기
    @Transactional
    public void updateMenuStock(int id, int stock){ menuDao.updateMenuStock(id, stock);}
}
