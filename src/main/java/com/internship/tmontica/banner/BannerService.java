package com.internship.tmontica.banner;

import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerDao bannerDao;

    private final JwtService jwtService;

    // 배너 등록하기
    public int addBanner(Banner banner){
        String role = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "role");
        if(!role.equals("admin"))
            return -1;  //TODO : 예외처리
        banner.setCreatorId(JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id"));
        return bannerDao.addBanner(banner);
    }

    // 배너 가져오기
    // 배너 수정하기
    // 배너 삭제하기
}
