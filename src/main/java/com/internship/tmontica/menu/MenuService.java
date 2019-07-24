package com.internship.tmontica.menu;

import com.internship.tmontica.option.Option;
import com.internship.tmontica.user.UserDao;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {
    private final MenuDao menuDao;
    private final UserDao userDao;

    // TODO : 예외처리..

    // 메뉴 추가
    @Transactional
    public int addMenu(Menu menu, List<Integer>optionIds, MultipartFile imgFile){
        // 1. 이미지 저장
        String imgUrl = saveImg(imgFile, menu.getNameEng());
        menu.setImgUrl(imgUrl);
        // 2. creator  id 체크 (exception 으로 바꾸자)
        if(userDao.getUserByUserId(menu.getCreatorId())== null)
            return -1;
        // 3. 메뉴 저장
        menu.setCreatedDate(new Date());
        menuDao.addMenu(menu);
        // 4. 메뉴 옵션 저장
        for(int optionId : optionIds)
            menuDao.addMenuOption(menu.getId(), optionId);
        // 5. 추가한 메뉴 아이디 리턴.
        log.info("created menu Id : {}", menu.getId());
        return menu.getId();
    }

    // 메뉴 옵션 추가
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

    // 이미지 파일 저장
    private String saveImg(MultipartFile imgFile, String name){
        // file url : imagefile/년/월/일/파일이름
        String dir = "imagefile/";
        Calendar calendar = Calendar.getInstance();
        dir = dir + calendar.get(Calendar.YEAR);
        dir = dir + "/";
        dir = dir + (calendar.get(Calendar.MONTH) + 1);
        dir = dir + "/";
        dir = dir + calendar.get(Calendar.DAY_OF_MONTH);
        dir = dir + "/";
        File dirFile = new File(dir);
        dirFile.mkdirs(); // 디렉토리가 없을 경우 만든다.
        dir += name;
        dir = dir + "_" +  UUID.randomUUID().toString();  // 유일한 식별자
        // 확장자
        String extension = imgFile.getOriginalFilename().split("\\.")[1];
        dir += "." + extension;

        log.info("img type : {}", extension);

        try(FileOutputStream fos = new FileOutputStream(dir);
            InputStream in = imgFile.getInputStream()){
            byte[] buffer = new byte[1024];
            int readCount = 0;
            while((readCount = in.read(buffer)) != -1){
                fos.write(buffer, 0, readCount);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return dir;
    }
}
