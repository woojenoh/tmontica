package com.internship.tmontica.cart;

import com.internship.tmontica.cart.exception.CartExceptionType;
import com.internship.tmontica.cart.exception.CartValidException;
import com.internship.tmontica.cart.model.request.CartReq;
import com.internship.tmontica.cart.model.request.CartUpdateReq;
import com.internship.tmontica.cart.model.response.CartIdResp;
import com.internship.tmontica.cart.model.response.CartResp;
import com.internship.tmontica.cart.validator.CartValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartMenuService cartMenuService;
    @Autowired
    private CartValidator cartValidator;

    /** 카트에 추가하기 */
    @PostMapping
    public ResponseEntity<List<CartIdResp>> addCart(@RequestBody @Valid List<CartReq> cartReqs, BindingResult bindingResult) {
        // 리스트를 validate
        cartValidator.validate(cartReqs, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new CartValidException(CartExceptionType.INVALID_CART_ADD_FORM, bindingResult);
        }
        List<CartIdResp> cartIds = cartMenuService.addCartApi(cartReqs);
        return new ResponseEntity<>(cartIds, HttpStatus.OK);
    }


    /** 카트 정보 가져오기 */
    @GetMapping
    public ResponseEntity<CartResp> getCartMenu() {
        CartResp cartResp = cartMenuService.getCartMenuApi();
        if(cartResp == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cartResp, HttpStatus.OK);
    }


    /** 카트 메뉴 수량, 가격 수정하기 */
    @PutMapping("/{id}")
    public ResponseEntity updateCartMenuQuantity(@PathVariable("id") int id, @RequestBody @Valid CartUpdateReq cartUpdateReq, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            throw new CartValidException(CartExceptionType.INVALID_CART_UPDATE_FORM, bindingResult);
        }
        int result = cartMenuService.updateCartApi(id, cartUpdateReq);
        if(result < 0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(HttpStatus.OK);
    }

    /** 카트 삭제하기 */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCartMenu(@PathVariable("id") int id){
        int result = cartMenuService.deleteCartApi(id);
        if(result > 0) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}

