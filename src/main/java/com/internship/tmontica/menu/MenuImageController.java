package com.internship.tmontica.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;

@Controller
public class MenuImageController {

    @Autowired
    MenuService menuService;

    @GetMapping("/images/{id}")
    @ResponseBody // 컨트롤러안에서 직접 response를 이용하여 결과를 출력할 때 사용
    public void downloadImage(@PathVariable(name = "id") int id, HttpServletResponse response) {
        Menu menu = menuService.getMenuById(id);

        try(FileInputStream fis = new FileInputStream(menu.getImgUrl());
            OutputStream out = response.getOutputStream()
        ){
            byte[] buffer = new byte[2048];
            int readCount = 0;

            while((readCount = fis.read(buffer)) != -1){
                out.write(buffer, 0, readCount);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
