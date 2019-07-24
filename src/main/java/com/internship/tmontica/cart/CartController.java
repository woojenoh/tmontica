package com.internship.tmontica.cart;

import com.internship.tmontica.cart.model.request.CartReq;
import com.internship.tmontica.cart.model.request.CartUpdateReq;
import com.internship.tmontica.cart.model.response.CartIdResp;
import com.internship.tmontica.cart.model.response.CartResp;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartMenuService cartMenuService;
    @Autowired
    private JwtService jwtService;

    /** 카트에 추가하기 */
    @PostMapping
    public ResponseEntity<List<CartIdResp>> addCart(@RequestBody @Valid List<CartReq> cartReqs) {

        List<CartIdResp> cartIds = cartMenuService.addCartApi(cartReqs);
        return new ResponseEntity(cartIds, HttpStatus.OK);
    }


    /** 카트 정보 가져오기 */
    @GetMapping
    public ResponseEntity<CartResp> getCartMenu() {

        CartResp cartResp = cartMenuService.getCartMenuApi();
        return new ResponseEntity(cartResp, HttpStatus.OK);
    }


    /** 카트 메뉴 수량, 가격 수정하기 */
    @PutMapping("/{id}")
    public ResponseEntity updateCartMenuQuantity(@PathVariable("id") int id, @RequestBody @Valid CartUpdateReq cartUpdateReq){
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
        // 토큰의 아이디와 카트 테이블의 userId 비교
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        String cartUserId = cartMenuService.getCartMenuByCartId(id).getUserId();
        if(userId.equals(cartUserId)){
            //카트에 담긴 정보 삭제하기
            int result = cartMenuService.deleteCartMenu(id);
            if(result > 0) return new ResponseEntity(HttpStatus.OK);
            else return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}

