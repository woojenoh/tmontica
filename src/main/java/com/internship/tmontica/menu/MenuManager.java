package com.internship.tmontica.menu;

import com.internship.tmontica.menu.exception.MenuException;
import com.internship.tmontica.menu.exception.MenuExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MenuManager {
    private final MenuDao menuDao;
    private static List<Menu> usableMenus;

    public MenuManager(MenuDao menuDao) {
        this.menuDao = menuDao;
        usableMenus = new ArrayList<>();
    }

    @Scheduled(cron = "0 * * * * *")
    public void filteredMenu() {
        log.info("[scheduler] start scheduler");
        List<Menu> allMenus = menuDao.getAllMenus();
        List<Menu> filteredMenus;
        Date now = new Date();

        Predicate<Menu> isDeleted = Menu::isDeleted;
        Predicate<Menu> con1 = menu -> menu.getStartDate() == null && menu.getEndDate() == null;
        Predicate<Menu> con2 = menu -> menu.getStartDate().before(now) && menu.getEndDate().after(now);

        filteredMenus = allMenus.stream().filter(Menu::isUsable).filter(isDeleted.negate())
                .filter(con1.or(con2)).collect(Collectors.toList());
        // read-only
        usableMenus = Collections.unmodifiableList(filteredMenus);

        log.info("[scheduler] end scheduler , usableMenus size : {}", usableMenus.size());
    }

    public static List<Menu> getUsableMenus() {
        return usableMenus;
    }

    public static List<Menu> getUsableMenusWithCheckEmpty(){
        if(usableMenus.isEmpty()){
            throw new MenuException(MenuExceptionType.MENU_NO_CONTENT_EXCEPTION);
        }
        return usableMenus;
    }

    public static Menu getMenuById(int id) {
        for (Menu checkMenu : usableMenus) {
            if (checkMenu.getId() == id) {
                return checkMenu;
            }
        }
        throw new MenuException(MenuExceptionType.MENU_NO_CONTENT_EXCEPTION);
    }


}
