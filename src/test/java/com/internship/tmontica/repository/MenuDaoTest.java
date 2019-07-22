package com.internship.tmontica.repository;

import com.internship.tmontica.dto.Menu;
import com.internship.tmontica.dto.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuDaoTest {
    @Autowired
    MenuDao menuDao;

//    @Test
//    public void 메뉴_가져오기(){
//        Menu menu = menuDao.getMenuById(2);
//    }

    @Test
    public void 메뉴들_가져오기(){
        List<Menu> menus = menuDao.getAllMenus();
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
}