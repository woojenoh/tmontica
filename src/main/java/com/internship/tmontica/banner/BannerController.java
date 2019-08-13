package com.internship.tmontica.banner;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    /** usePage에 맞는 배너 가져오기.**/
    @GetMapping("/{usePage}")
    public ResponseEntity<List<Banner>> getBannerByUsePage(@PathVariable UsePage usePage){
        List<Banner> banners = bannerService.getBannersByPage(usePage);
        return new ResponseEntity<>(banners, HttpStatus.OK);
    }


}
