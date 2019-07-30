package com.internship.tmontica.banner;

import com.internship.tmontica.banner.model.request.BannerReq;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createBanner(@ModelAttribute @Valid BannerReq bannerReq , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Banner banner = modelMapper.map(bannerReq, Banner.class);
        int id = bannerService.addBanner(banner);
        if(id == -1)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
