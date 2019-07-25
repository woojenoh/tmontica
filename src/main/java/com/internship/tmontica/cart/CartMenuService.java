package com.internship.tmontica.cart;

import com.internship.tmontica.cart.model.request.CartReq;
import com.internship.tmontica.cart.model.request.CartUpdateReq;
import com.internship.tmontica.cart.model.request.Cart_OptionReq;
import com.internship.tmontica.cart.model.response.CartIdResp;
import com.internship.tmontica.cart.model.response.CartResp;
import com.internship.tmontica.cart.model.response.Cart_MenusResp;
import com.internship.tmontica.menu.Menu;
import com.internship.tmontica.menu.MenuDao;
import com.internship.tmontica.option.Option;
import com.internship.tmontica.option.OptionDao;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.security.JwtServiceImpl;
import com.internship.tmontica.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class CartMenuService {

    private final CartMenuDao cartMenuDao;
    private final OptionDao optionDao;
    private final MenuDao menuDao;
    private final JwtService jwtService;

    // 카트 정보 가져오기 api
    public CartResp getCartMenuApi(){
        // 토큰에서 아이디 가져오기
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id");

        List<Cart_MenusResp> menus = new ArrayList<>(); // 반환할 객체 안의 menus에 들어갈 리스트

        // userId로 카트메뉴 정보 가져오기
        List<CartMenu> cartMenus = cartMenuDao.getCartMenuByUserId(userId);
        int size = 0;
        int totalPrice = 0;
        for (CartMenu cartMenu: cartMenus) {
            // 메뉴 옵션 "1__1/4__2" => "HOT/샷추가(2개)" 로 바꾸는 작업
            String option = convertOptionStringToCli(cartMenu.getOption());
            // 메뉴아이디로 메뉴정보 가져오기
            Menu menu = menuDao.getMenuById(cartMenu.getMenuId());
            int price = menu.getSellPrice()+cartMenu.getPrice(); // 메뉴가격 + 옵션가격
            // List<Cart_MenusResp> 에 넣기
            Cart_MenusResp cart_menusResp = new Cart_MenusResp(cartMenu.getId(), cartMenu.getMenuId(), menu.getNameEng(),
                                                                menu.getNameKo(),menu.getImgUrl(), option ,
                                                                cartMenu.getQuantity(), price, menu.getStock());
            menus.add(cart_menusResp);
            // totalPrice 에 가격 누적
            totalPrice += (price *  cartMenu.getQuantity());
            // size에 quantity 누적
            size += cartMenu.getQuantity();
        }
        return new CartResp(size, totalPrice, menus); // 반환할 객체
    }


    // 카트에 추가하기 api
    public List<CartIdResp> addCartApi(List<CartReq> cartReqs){
        List<CartIdResp> cartIds = new ArrayList<>();

        for (CartReq cartReq: cartReqs) {
            // direct : true 이면 카트에서 direct = true 인 것을 삭제하기
            if (cartReq.isDirect()) {
                cartMenuDao.deleteDirectCartMenu();
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
                optionPrice += ((optionDao.getOptionById(optionId).getPrice()) * opQuantity);
            }

            // 옵션, 수량 적용된 가격 계산하기
//            int price = optionPrice + menuDao.getMenuById(cartReq.getMenuId()).getSellPrice();
//            price *= cartReq.getQuantity();

            // 토큰에서 userId 가져오기
            String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");

            // 카트 테이블에 추가하기
            CartMenu cartMenu = new CartMenu(cartReq.getQuantity(), optionStr, userId,
                    optionPrice, cartReq.getMenuId(), cartReq.isDirect());
            int result = cartMenuDao.addCartMenu(cartMenu);
            int cartId = cartMenu.getId();

            cartIds.add(new CartIdResp(cartId));

        }//List for end

        return cartIds;
    }

    // 카트 수정하기 api
    public ResponseEntity updateCartApi(int id, CartUpdateReq cartUpdateReq){
        int result = cartMenuDao.updateCartMenuQuantity(id, cartUpdateReq.getQuantity());
        if(result < 0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 카트 삭제하기 api
    public ResponseEntity deleteCartApi(int id){
        // 토큰의 아이디와 카트 테이블의 userId 비교
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        String cartUserId = cartMenuDao.getCartMenuByCartId(id).getUserId();
        if(userId.equals(cartUserId)){
            //카트에 담긴 정보 삭제하기
            int result = cartMenuDao.deleteCartMenu(id);
            if(result > 0) return new ResponseEntity(HttpStatus.OK);
            else return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }


    // DB의 옵션 문자열을 변환
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
            } else if(tmpOption.getType().equals("Shot")){
                convert += "샷추가" + "(" + oneOption[1] + "개)";
            } else if(tmpOption.getType().equals("Syrup")){
                convert += "시럽추가" + "(" + oneOption[1] + "개)";
            } else if(tmpOption.getType().equals("Size")){
                convert += "사이즈업" + "(" + oneOption[1] + "개)";
            }
        }
        return convert;
    }
}
