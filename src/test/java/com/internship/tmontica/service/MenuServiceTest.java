package com.internship.tmontica.service;

import com.internship.tmontica.dto.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceTest {
    @Autowired
    MenuService menuService;

    @Test
    public void 메뉴_가져오기(){
        List<Menu> menus= menuService.getAllMenus();
        for(Menu menu : menus)
            System.out.println(menu.toString());
    }

}