package com.internship.tmontica.menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuScheduler {
    private final MenuDao menuDao;

    @Scheduled(fixedDelay = 60000)
    public void filteredMenu(){
        log.info("[scheduler] start scheduler");
        List<Menu> allMenus = menuDao.getAllMenus();
        List<Menu> filteredMenus = new ArrayList<>();
        Date now = new Date();

        for(Menu menu : allMenus){
            if(menu.getStartDate() == null && menu.getEndDate() == null) {
                if(menu.isUsable())
                    filteredMenus.add(menu);
            }else if(menu.getStartDate().before(now) && menu.getEndDate().after(now)){
                filteredMenus.add(menu);
                log.info("[limited menu] : {}" + menu);
            }
        }

        MenuService.usableMenus = filteredMenus;
        log.info("[scheduler] end scheduler , usableMenus size : {}", MenuService.usableMenus.size());
    }

}
