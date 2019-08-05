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

    private final ModelMapper modelMapper;

    // usePage에 맞는 banner 가져오기.
    @GetMapping("/{usePageEng:[a-z-]+}")
    public ResponseEntity<List<Banner>> getBannerByNumber(@PathVariable String usePageEng){
        List<Banner> banners = bannerService.getBannersByPage(usePageEng);
        return new ResponseEntity<>(banners, HttpStatus.OK);
    }


}
