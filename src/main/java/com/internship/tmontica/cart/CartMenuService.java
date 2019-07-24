package com.internship.tmontica.cart;

import com.internship.tmontica.cart.model.response.CartResp;
import com.internship.tmontica.cart.model.response.Cart_MenusResp;
import com.internship.tmontica.menu.Menu;
import com.internship.tmontica.menu.MenuDao;
import com.internship.tmontica.option.Option;
import com.internship.tmontica.option.OptionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartMenuService {

    private final CartMenuDao cartMenuDao;
    private final OptionDao optionDao;
    private final MenuDao menuDao;

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


    // 카트 정보 가져오기 api
    public CartResp getCartMenuApi(String userId){
        List<Cart_MenusResp> menus = new ArrayList<>(); // 반환할 객체 안의 menus에 들어갈 리스트

        // userId로 카트메뉴 정보 가져오기
        List<CartMenu> cartMenus = cartMenuDao.getCartMenuByUserId(userId);
        int totalPrice = 0;
        for (CartMenu cartMenu: cartMenus) {

            //메뉴 옵션 "1__1/4__2" => "HOT/샷추가(2개)" 로 바꾸는 작업
            String option = convertOptionStringToCli(cartMenu.getOption());

            // 메뉴아이디로 메뉴정보 가져오기
            Menu menu = menuDao.getMenuById(cartMenu.getMenuId());

            // List<Cart_MenusResp> 에 넣기
            Cart_MenusResp cart_menusResp = new Cart_MenusResp(cartMenu.getId(), cartMenu.getMenuId(), menu.getNameEng(),
                                                                menu.getNameKo(),menu.getImgUrl(), option ,
                                                                cartMenu.getQuantity(), cartMenu.getPrice(), menu.getStock());
            menus.add(cart_menusResp);

            // totalPrice 에 가격 누적
            totalPrice += cartMenu.getPrice();
        }

        return new CartResp(cartMenus.size(), totalPrice, menus); // 반환할 객체
    }


    public String convertOptionStringToCli(String option){
        //메뉴 옵션 "1__1/4__2" => "HOT/샷추가(2개)" 로 바꾸는 작업
        //String option = cartMenu.getOption();
        String convert = ""; // 변환할 문자열
        String[] arrOption = option.split("/");
        for (int j = 0; j < arrOption.length; j++) {
            String[] oneOption = arrOption[j].split("__");
            Option tmpOption = optionDao.getOptionById(Integer.valueOf(oneOption[0]));
            if (j != 0) {
                convert += "/";
            }
            if (tmpOption.getType().equals("Temperature")) {
                convert += tmpOption.getName();
            } else {
                convert += tmpOption.getName() + "(" + oneOption[1] + "개)";
            }
        }
        return convert;
    }
}
