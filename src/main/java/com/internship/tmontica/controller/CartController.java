package com.internship.tmontica.controller;

import com.internship.tmontica.dto.CartMenu;
import com.internship.tmontica.dto.Option;
import com.internship.tmontica.dto.request.CartReq;
import com.internship.tmontica.dto.request.Cart_OptionReq;
import com.internship.tmontica.service.CartMenuService;
import com.internship.tmontica.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartMenuService cartMenuService;
    @Autowired
    private OptionService optionService;

    /** 카트에 추가하기 */
    @PostMapping
    public ResponseEntity<Map<String, Integer>> addCart(@RequestBody @Valid CartReq cartReq){
        // direct : true 이면 카트에서 direct = true 인 것을 삭제하기
        if(cartReq.isDirect() == true){
            cartMenuService.deleteDirectCartMenu();
        }

        List<Cart_OptionReq> option = cartReq.getOption();
        String optionStr = "";
        int optionPrice = 0;
        for (int i = 0; i < option.size(); i++) {
            // DB에 들어갈 옵션 문자열 만들기
            if(i != 0) { optionStr += "/"; }
            int optionId = option.get(i).getId();
            int amount = option.get(i).getAmount();
            optionStr += optionId+"__"+amount;

            // 옵션들의 가격 계산하기
            Option tmpOption = optionService.getOptionById(optionId);
            optionPrice += (tmpOption.getPrice() * amount);
        }

        optionPrice *= cartReq.getQuantity();

        CartMenu cartMenu = new CartMenu(cartReq.getQuantity(), optionStr, cartReq.getUserId(),
                                            optionPrice ,cartReq.getMenuId(), cartReq.isDirect());
        // 카트 테이블에 추가하기
        cartMenuService.addCartMenu(cartMenu);
        int cartId = cartMenu.getId();
        Map<String, Integer> map = new HashMap<>(); // 반환값 cartId
        map.put("cartId", cartId);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    /** 카트 정보 가져오기 */
//    @GetMapping
//    public ResponseEntity getCartMenu(@RequestParam String userId){
//
//
//    }
}
