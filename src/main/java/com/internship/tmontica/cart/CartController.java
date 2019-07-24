package com.internship.tmontica.cart;

import com.internship.tmontica.menu.Menu;
import com.internship.tmontica.option.Option;
import com.internship.tmontica.cart.model.request.CartReq;
import com.internship.tmontica.cart.model.request.CartUpdateReq;
import com.internship.tmontica.cart.model.request.Cart_OptionReq;
import com.internship.tmontica.cart.model.response.CartResp;
import com.internship.tmontica.cart.model.response.Cart_MenusResp;
import com.internship.tmontica.menu.MenuService;
import com.internship.tmontica.option.OptionService;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    @Autowired
    private MenuService menuService;
    @Autowired
    private JwtService jwtService;
    /** 카트에 추가하기 */
    @PostMapping
    public ResponseEntity<Map<String, Integer>> addCart(@RequestBody @Valid CartReq cartReq) {
        // direct : true 이면 카트에서 direct = true 인 것을 삭제하기
        if (cartReq.isDirect() == true) {
            cartMenuService.deleteDirectCartMenu();
        }

        List<Cart_OptionReq> option = cartReq.getOption();
        String optionStr = "";
        int optionPrice = 0;
        for (int i = 0; i < option.size(); i++) {
            // DB에 들어갈 옵션 문자열 만들기
            if (i != 0) {
                optionStr += "/";
            }
            int optionId = option.get(i).getId();
            int opQuantity = option.get(i).getQuantity();
            optionStr += optionId + "__" + opQuantity;

            // 옵션들의 가격 계산
            Option tmpOption = optionService.getOptionById(optionId);
            optionPrice += (tmpOption.getPrice() * opQuantity);
        }

        // 옵션, 수량 적용된 가격 계산하기
        int price = optionPrice + menuService.getMenuById(cartReq.getMenuId()).getSellPrice();
        price *= cartReq.getQuantity();
        // 토큰에서 userId 가져오기
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        CartMenu cartMenu = new CartMenu(cartReq.getQuantity(), optionStr, userId,
                                            price, cartReq.getMenuId(), cartReq.isDirect());
        // 카트 테이블에 추가하기
        int result = cartMenuService.addCartMenu(cartMenu);
        int cartId = cartMenu.getId();
        Map<String, Integer> map = new HashMap<>(); // 반환값 cartId
        map.put("cartId", cartId);

        if(result > 0)return new ResponseEntity<>(map, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    /** 카트 정보 가져오기 */
    @GetMapping
    public ResponseEntity<CartResp> getCartMenu(@RequestParam String userId) {
        System.out.println(userId + " getCart controller in");
        // 파라미터로 받은 userId와 토큰의 userId가 같은지 검사
        String tokenUserId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id");
        System.out.println(tokenUserId);
        if(tokenUserId.equals(userId)){
            CartResp cartResp = cartMenuService.getCartMenuApi(userId);

            return new ResponseEntity(cartResp,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    /** 카트 메뉴 수량, 가격 수정하기 */
    @PutMapping("/{id}")
    public ResponseEntity updateCartMenuQuantity(@PathVariable("id") int id,
                                                 @RequestBody @Valid CartUpdateReq cartUpdateReq){
        // 수량과 가격 모두 수정해야 함
        CartMenu cartMenu = cartMenuService.getCartMenuByCartId(id);
        int unitPrice = cartMenu.getPrice() / cartMenu.getQuantity(); // 해당 카트메뉴의 1개 가격
        // 수량와 가격 업데이트
        int result = cartMenuService.updateCartMenuQuantity(id, unitPrice * cartUpdateReq.getQuantity(), cartUpdateReq.getQuantity());

        if(result > 0 ) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /** 카트 삭제하기 */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCartMenu(@PathVariable("id") int id){
        //카트에 담긴 정보 삭제하기
        int result = cartMenuService.deleteCartMenu(id);

        if(result > 0) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}

