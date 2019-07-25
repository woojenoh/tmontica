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

    /** 카트에 추가하기 */
    @PostMapping
    public ResponseEntity<?> addCart(@RequestBody @Valid List<CartReq> cartReqs) {
        List<CartIdResp> cartIds = cartMenuService.addCartApi(cartReqs);
        //if(cartIds == null)return new ResponseEntity<>("카트에 추가 실패",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(cartIds, HttpStatus.OK);
    }


    /** 카트 정보 가져오기 */
    @GetMapping
    public ResponseEntity<CartResp> getCartMenu() {
        CartResp cartResp = cartMenuService.getCartMenuApi();
        return new ResponseEntity<>(cartResp, HttpStatus.OK);
    }


    /** 카트 메뉴 수량, 가격 수정하기 */
    @PutMapping("/{id}")
    public ResponseEntity updateCartMenuQuantity(@PathVariable("id") int id, @RequestBody @Valid CartUpdateReq cartUpdateReq){
        return cartMenuService.updateCartApi(id, cartUpdateReq);
    }

    /** 카트 삭제하기 */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCartMenu(@PathVariable("id") int id){
        return cartMenuService.deleteCartApi(id);
    }
}

