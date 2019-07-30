package com.internship.tmontica.menu;

import com.internship.tmontica.menu.exception.SaveImgException;
import com.internship.tmontica.menu.model.response.MenuDetailResp;
import com.internship.tmontica.menu.model.response.MenuMainResp;
import com.internship.tmontica.menu.model.response.MenuOptionResp;
import com.internship.tmontica.menu.model.response.MenuSimpleResp;
import com.internship.tmontica.option.Option;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.util.CategoryName;
import com.internship.tmontica.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
//@RequiredArgsConstructor
public class MenuService {

    private final MenuDao menuDao;

    private final ModelMapper modelMapper;

    private final JwtService jwtService;

    @Value("${menu.imagepath}")
    private String location;

    public static List<Menu> usableMenus;

    public MenuService(MenuDao menuDao, ModelMapper modelMapper, JwtService jwtService){
        this.menuDao = menuDao;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        usableMenus = new ArrayList<>();
    }

    // TODO : 예외처리..
    // 메인 화면에 나타나는 메뉴 정보
    public List<MenuMainResp> getMainMenus(){

        List<MenuMainResp> allMenus = new ArrayList<>();
        // 이달의 메뉴 , 커피 , 에이드 , 빵
        allMenus.add(getMenuMainResp(CategoryName.CATEGORY_MONTHLY));
        allMenus.add(getMenuMainResp(CategoryName.CATEGORY_COFFEE));
        allMenus.add(getMenuMainResp(CategoryName.CATEGORY_ADE));
        allMenus.add(getMenuMainResp(CategoryName.CATEGORY_BREAD));

        return allMenus;
    }

    // 메뉴 추가
    @Transactional
    public int addMenu(Menu menu, List<Integer>optionIds, MultipartFile imgFile){
        String imgUrl = saveImg(imgFile, menu.getNameEng());
        menu.setImgUrl(imgUrl);

        // 등록인 정보 가져오기
        menu.setCreatorId(JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id"));
        menu.setCreatedDate(new Date());
        menuDao.addMenu(menu);

        // 메뉴의 옵션 추가
        for(int optionId : optionIds)
            menuDao.addMenuOption(menu.getId(), optionId);

        log.info("created menu Id : {}", menu.getId());
        return menu.getId();
    }

    // 하나의 메뉴 상세 정보 가져오기
    public MenuDetailResp getMenuDetailById(int id){
        Menu menu = null;
        for(Menu checkMenu : usableMenus){
            if(checkMenu.getId() == id){
                menu = checkMenu;
                break;
            }
        }
        if(menu == null) return null;

        // 메뉴의 옵션 정보 가져오기
        List<Option> options = menuDao.getOptionsById(id);

        MenuDetailResp menuDetailResp = modelMapper.map(menu, MenuDetailResp.class);
        List<MenuOptionResp> menuOptions = modelMapper.map(options, new TypeToken<List<MenuOptionResp>>(){}.getType());

        menuDetailResp.setOption(menuOptions);
        menuDetailResp.setImgUrl("/images/".concat(menuDetailResp.getImgUrl()));

        return menuDetailResp;
    }

    // 카테고리 별 메뉴 정보 가져오기
    public List<Menu> getMenusByCategory(String category, int page, int size){
        long startTime = System.currentTimeMillis();
        // 해당 카테고리의 모든 메뉴를 가져온다.
        List<Menu> categoryMenus = usableMenus.stream().filter(menu -> menu.getCategoryEng().equals(category))
                                              .collect(Collectors.toList());
        // 가져온 메뉴들 중 페이지에 맞는 메뉴들만 리턴한다.
        long endTime = System.currentTimeMillis();
        log.info("getMenuByCategory 실행 시간 : {} ", endTime - startTime);
        return getMenusByPage(page, size, categoryMenus);

    }

    // 메뉴 정보 가져오기 (전체)
    public List<Menu> getAllMenus(int page, int size){
        // 페이지에 맞는 메뉴들만 리턴한다.
        return getMenusByPage(page, size, usableMenus);

    }

    private List<Menu> getMenusByPage(int page, int size, List<Menu> menus) {
        int startIndex = (page - 1) * size;

        if(startIndex < 0 || startIndex >= menus.size())
            return new ArrayList<>();

        int endIndex = (startIndex + size < menus.size())? startIndex + size : menus.size();

        return menus.subList(startIndex, endIndex);
    }

    // 메뉴 옵션 추가
    public int addMenuOption(int menuId, int optionId){
        return menuDao.addMenuOption(menuId, optionId);
    }

    // 하나의 메뉴 정보 가져오기
    public Menu getMenuById(int id){
        return menuDao.getMenuById(id);
    }

    // 메뉴 수정하기
    public void updateMenu(Menu menu, MultipartFile imgFile){
        if(!existMenu(menu.getId()))
            return;

        menu.setUpdaterId(JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id"));

        if(imgFile!=null){
            String img = saveImg(imgFile, menu.getNameEng());
            menu.setImgUrl(img);
        }else{
            Menu beforeMenu = getMenuById(menu.getId());
            menu.setImgUrl(beforeMenu.getImgUrl());
        }
        menu.setUpdatedDate(new Date());
        menuDao.updateMenu(menu);
    }

    // 메뉴 삭제하기
    public void deleteMenu(int id) {
        menuDao.deleteMenu(id);
    }

    // 수량 수정하기
    public void updateMenuStock(int id, int stock){
        menuDao.updateMenuStock(id, stock);
    }

    // 메뉴 존재하는지 확인
    public boolean existMenu(int id){
        return (menuDao.getMenuById(id) == null) ? false : true;
    }

    // 메인 화면에 필요한 메뉴정보 가져오기.
    private MenuMainResp getMenuMainResp(String categoryEng){
        // 메인 화면에 나타나는 메뉴 정보
        MenuMainResp menuMainResp = new MenuMainResp();
        menuMainResp.setCategoryEng(categoryEng);
        menuMainResp.setCategoryKo(CategoryName.categoryEngToKo(categoryEng));
        List<Menu> menus = new ArrayList<>();

        int count = 0;
        if(categoryEng.equals(CategoryName.CATEGORY_MONTHLY)){  // 4개 까지 가져온다.
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

        for(MenuSimpleResp menu : menuSimpleList)
            menu.setImgUrl("/images/".concat(menu.getImgUrl()));

        menuMainResp.setMenus(menuSimpleList);
        return menuMainResp;
    }

    // 이미지 파일 저장
    private String saveImg(MultipartFile imgFile, String name){

        // file url : imagefile/년/월/일/파일이름
        StringBuilder sb = new StringBuilder("imagefile/");
        Calendar calendar = Calendar.getInstance();
        sb.append(calendar.get(Calendar.YEAR)).append("/");
        sb.append(calendar.get(Calendar.MONTH) + 1).append("/");
        sb.append(calendar.get(Calendar.DAY_OF_MONTH)).append("/");
        // 실제 저장되는 path
        File dirFile = new File(location + sb.toString());
        dirFile.mkdirs(); // 디렉토리가 없을 경우 만든다.

        sb.append(name).append("_").append(UUID.randomUUID().toString()); // 유일한 식별자
        // 확장자 가져오기.
        String extension = getExtensionByStringHandling(imgFile.getOriginalFilename())
                                                                .orElseThrow(() -> new SaveImgException());
        sb.append(".").append(extension);

        log.info("img type : {}", extension);
        String dir = sb.toString();
        try(FileOutputStream fos = new FileOutputStream(location.concat(dir));
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

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

}
