package com.internship.tmontica.banner;

import com.internship.tmontica.banner.exception.BannerException;
import com.internship.tmontica.banner.exception.BannerExceptionType;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.util.JsonUtil;
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
    public List<Banner> getBannersByPage(String usePage){
        checkUsePageEng(usePage);
        List<Banner> banners = bannerDao.getBannersByUsePage(usePage);
        return banners;
    }

    public void checkUsePageEng(String usePageEng){

        for(BannerUsePage bannerUsePage : BannerUsePage.values()){
            if(bannerUsePage.getUsePageEng().equals(usePageEng)){
                return;
            }
        }
        throw new BannerException(BannerExceptionType.USEPAGE_MISMATCH_EXCEPTION);
    }
}
