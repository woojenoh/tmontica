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

        CartMenu cartMenu = new CartMenu(cartReq.getQuantity(), optionStr, cartReq.getUserId(),
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
        List<Cart_MenusResp> menus = new ArrayList<>(); // 반환할 객체 안의 menus에 들어갈 리스트

        // userId로 카트메뉴 정보 가져오기
        List<CartMenu> cartMenus = cartMenuService.getCartMenuByUserId(userId);
        int totalPrice = 0;
        for (CartMenu cartMenu: cartMenus) {

            //메뉴 옵션 "1__1/4__2" => "HOT/샷추가(2개)" 로 바꾸는 작업
            String option = cartMenu.getOption();
            String convert = ""; // 변환할 문자열
            String[] arrOption = option.split("/");
            for (int j = 0; j < arrOption.length; j++) {
                String[] oneOption = arrOption[j].split("__");
                Option tmpOption = optionService.getOptionById(Integer.valueOf(oneOption[0]));
                if (j != 0) {
                    convert += "/";
                }
                if (tmpOption.getType().equals("Temperature")) {
                    convert += tmpOption.getName();
                } else {
                    convert += tmpOption.getName() + "(" + oneOption[1] + "개)";
                }
            }

            // 메뉴아이디로 메뉴정보 가져오기
            Menu menu = menuService.getMenuById(cartMenu.getMenuId());

            // List<Cart_MenusResp> 에 넣기
            Cart_MenusResp cart_menusResp = new Cart_MenusResp(cartMenu.getId(), cartMenu.getMenuId(), menu.getNameEng(),
                                                                menu.getNameKo(),menu.getImgUrl(), convert ,
                                                                cartMenu.getQuantity(), cartMenu.getPrice(), menu.getStock());
            menus.add(cart_menusResp);

            // totalPrice 에 가격 누적
            totalPrice += cartMenu.getPrice();
        }

        CartResp cartResp = new CartResp(cartMenus.size(), totalPrice, menus); // 반환할 객체

        return new ResponseEntity(cartResp,HttpStatus.OK);
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

