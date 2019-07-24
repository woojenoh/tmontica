package com.internship.tmontica.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartMenuService {

    private final CartMenuDao cartMenuDao;

    // 카트 아이디로 cart_menus 테이블에서 조회하기
    @Transactional(readOnly = true)
    public CartMenu getCartMenuByCartId(int cartId){ return cartMenuDao.getCartMenuByCartId(cartId); }

    // 카트에서 삭제하기
    public int deleteCartMenu(int cartId){ return cartMenuDao.deleteCartMenu(cartId); }

    // 카트에 추가하기
    public int addCartMenu(CartMenu cartMenu){ return cartMenuDao.addCartMenu(cartMenu); }

    // direct = true 인 카트메뉴 삭제하기
    public void deleteDirectCartMenu(){
        cartMenuDao.deleteDirectCartMenu();
    }

    // userId로 cart_menus 조회하기
    @Transactional(readOnly = true)
    public List<CartMenu> getCartMenuByUserId(String userId){
       return cartMenuDao.getCartMenuByUserId(userId);
    }

    // 카트에서 수량과 가격 수정하기
    public int updateCartMenuQuantity(int id, int price, int quantity){
        return cartMenuDao.updateCartMenuQuantity(id, price, quantity);
    }
}
