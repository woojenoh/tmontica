package com.internship.tmontica.banner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BannerServiceTest {
    @Mock
    private BannerDao bannerDao;

    @InjectMocks
    private BannerService bannerService;

    private List<Banner> banners = new ArrayList<>();

    @Before
    public void setUp() throws Exception{

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Banner banner1 = Banner.builder().id(1).link("http://tmontica-idev.tmon.co.kr/")
                .usePage(BannerUsePage.BANNER_MAIN_TOP.getUsePageEng()).creatorId("admin")
                .imgUrl("/imagefiles/2019/10/1/banner1.png").startDate(sdf.parse("2018-08-28 17:22:21"))
                .endDate(sdf.parse("2020-08-28 17:22:21")).number(1).build();

        Banner banner2 = Banner.builder().id(2).link("http://tmontica-idev.tmon.co.kr/")
                .usePage(BannerUsePage.BANNER_MAIN_TOP.getUsePageEng()).creatorId("admin")
                .imgUrl("/imagefiles/2019/10/1/banner2.png").startDate(sdf.parse("2018-08-28 17:22:21"))
                .endDate(sdf.parse("2020-08-28 17:22:21")).number(2).build();

        Banner banner3 = Banner.builder().id(3).link("http://tmontica-idev.tmon.co.kr/")
                .usePage(BannerUsePage.BANNER_MAIN_BOTTOM.getUsePageEng()).creatorId("admin")
                .imgUrl("/imagefiles/2019/10/1/banner3.png").startDate(sdf.parse("2018-08-28 17:22:21"))
                .endDate(sdf.parse("2020-08-28 17:22:21")).number(1).build();

        banners.add(banner1);
        banners.add(banner2);
        banners.add(banner3);

    }

    @Test
    public void userPage로_배너_가져오기() {
        List<Banner> mainTopBanners = banners.stream().filter(b -> b.getUsePage().equals(BannerUsePage.BANNER_MAIN_TOP.getUsePageEng())).collect(Collectors.toList());

        //given
        given(bannerDao.getBannersByUsePage(BannerUsePage.BANNER_MAIN_TOP.getUsePageEng())).willReturn(mainTopBanners);

        //when
        List<Banner> result = bannerService.getBannersByPage(BannerUsePage.BANNER_MAIN_TOP.getUsePageEng());

        //then
        verify(bannerDao, atLeastOnce()).getBannersByUsePage(anyString());
        assertEquals(mainTopBanners, result);
    }
}