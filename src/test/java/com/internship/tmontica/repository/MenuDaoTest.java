package com.internship.tmontica.repository;

import com.internship.tmontica.menu.Menu;
import com.internship.tmontica.option.Option;
import com.internship.tmontica.menu.MenuDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application_test.properties")
@SpringBootTest
public class MenuDaoTest {
    @Autowired
    MenuDao menuDao;

    @Test
    public void 메뉴_가져오기(){
        Menu menu = menuDao.getMenuById(2);
        assertEquals(menu.getNameKo(), "자몽에이드");
    }

    @Test
    public void 메뉴들_가져오기(){
        List<Menu> menus = menuDao.getAllMenus();
        for(Menu m : menus)
            System.out.println(m.toString());
    }

    @Test
    public void 메뉴_추가하기(){
//        Menu menu = Menu.builder()
//                    .nameKo("카푸치노").nameEng("cappuccino").categoryKo("커피").categoryEng("coffee")
//                    .createdDate(())
    }

//    @Test
//    public void 메뉴_옵션_추가(){
//        menuDao.addMenuOption(1,2);
//    }

    @Test
    public void 메뉴의_옵션_가져오기(){
        List<Option> options = menuDao.getOptionsById(1);
        for(Option o : options)
            System.out.println(o.getName());
    }

    @Test
    public void 메뉴_수량_수정하기(){
        int beforeStock = menuDao.getMenuById(1).getStock();
        menuDao.updateMenuStock(1, beforeStock - 2);
        assertEquals(menuDao.getMenuById(1).getStock(), beforeStock-2);
    }
}