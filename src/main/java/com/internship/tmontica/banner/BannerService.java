package com.internship.tmontica.banner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerDao bannerDao;

    // usePage로 배너 가져오기
    public List<Banner> getBannersByPage(UsePage usePage){
        List<Banner> banners = bannerDao.getBannersByUsePage(usePage.toString());
        return banners;
    }
}
