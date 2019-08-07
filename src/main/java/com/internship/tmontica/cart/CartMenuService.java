package com.internship.tmontica.cart;

import com.internship.tmontica.cart.exception.CartExceptionType;
import com.internship.tmontica.cart.exception.CartException;
import com.internship.tmontica.cart.model.request.CartReq;
import com.internship.tmontica.cart.model.request.CartUpdateReq;
import com.internship.tmontica.cart.model.request.CartOptionReq;
import com.internship.tmontica.cart.model.response.CartIdResp;
import com.internship.tmontica.cart.model.response.CartResp;
import com.internship.tmontica.cart.model.response.CartMenusResp;
import com.internship.tmontica.menu.Menu;
import com.internship.tmontica.menu.MenuDao;
import com.internship.tmontica.option.Option;
import com.internship.tmontica.option.OptionDao;
import com.internship.tmontica.order.exception.NotEnoughStockException;
import com.internship.tmontica.order.exception.StockExceptionType;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartMenuService {

    private final CartMenuDao cartMenuDao;
    private final OptionDao optionDao;
    private final MenuDao menuDao;
    private final JwtService jwtService;

    // 카트 정보 가져오기 api
    public CartResp getCartMenuApi(){
        // 토큰에서 아이디 가져오기
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id");

        List<CartMenusResp> menus = new ArrayList<>(); // 반환할 객체 안의 menus에 들어갈 리스트

        // userId로 카트메뉴 정보 가져오기
        List<CartMenu> cartMenus = cartMenuDao.getCartMenuByUserId(userId);
        int size = 0;
        int totalPrice = 0;
        for (CartMenu cartMenu: cartMenus) {
            // 메뉴 옵션 "1__1/4__2" => "HOT/샷추가(2개)" 로 바꾸는 작업
            String option = "";
            if(!cartMenu.getOption().equals("")){
                option = convertOptionStringToCli(cartMenu.getOption());
            }
            // 메뉴아이디로 메뉴정보 가져오기
            Menu menu = menuDao.getMenuById(cartMenu.getMenuId());
            int price = menu.getSellPrice()+cartMenu.getPrice(); // 메뉴가격 + 옵션가격

            // List<CartMenusResp> 에 넣기
            CartMenusResp cartMenusResp = new CartMenusResp(cartMenu.getId(), cartMenu.getMenuId(), menu.getNameEng(),
                                                                menu.getNameKo(),"/images/".concat(menu.getImgUrl()), option ,
                                                                cartMenu.getQuantity(), price, menu.getStock());
            menus.add(cartMenusResp);
            // totalPrice 에 가격 누적
            totalPrice += (price *  cartMenu.getQuantity());
            // size에 quantity 누적
            size += cartMenu.getQuantity();
        }
        return new CartResp(size, totalPrice, menus); // 반환할 객체
    }


    // 카트에 추가하기 api
    @Transactional
    public List<CartIdResp> addCartApi(List<CartReq> cartReqs){
        List<CartIdResp> cartIds = new ArrayList<>();
        // 토큰에서 userId 가져오기
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");

        for (CartReq cartReq: cartReqs) {
            int stock = menuDao.getMenuById(cartReq.getMenuId()).getStock(); // 메뉴의 현재 재고량
            int quantity = cartReq.getQuantity(); // 차감할 메뉴의 수량
            if(stock-quantity < 0){ // 재고가 모자랄 경우 rollback
                throw new NotEnoughStockException(cartReq.getMenuId(), StockExceptionType.NOT_ENOUGH_STOCK);
            }

            // direct : true 이면 userId 의 카트에서 direct = true 인 것을 삭제하기
            if (cartReq.getDirect()) {
                cartMenuDao.deleteDirectCartMenu(userId);
            }

            List<CartOptionReq> options = cartReq.getOption();
            StringBuilder optionStr = new StringBuilder();
            int optionPrice = 0;
            for (CartOptionReq option : options) {
                // DB에 들어갈 옵션 문자열 만들기
                if (option.getId() > 2) {
                    optionStr.append("/");
                }
                int optionId = option.getId();
                int opQuantity = option.getQuantity();
                optionStr.append(optionId + "__" + opQuantity);

                // 옵션들의 가격 계산
                optionPrice += ((optionDao.getOptionById(optionId).getPrice()) * opQuantity);
            }

            // 카트 테이블에 추가하기
            CartMenu cartMenu = new CartMenu(cartReq.getQuantity(), optionStr.toString(), userId,
                    optionPrice, cartReq.getMenuId(), cartReq.getDirect());
            cartMenuDao.addCartMenu(cartMenu);
            int cartId = cartMenu.getId();

            cartIds.add(new CartIdResp(cartId));

        }//List forEach end

        return cartIds;
    }

    // 카트 수정하기 api
    public int updateCartApi(int id, CartUpdateReq cartUpdateReq){
        // 토큰의 아이디와 카트 테이블의 userId 비교
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        String cartUserId = cartMenuDao.getCartMenuByCartId(id).getUserId();
        if(!userId.equals(cartUserId)){
            // 아이디 일치하지 않을 경우
            throw new CartException(CartExceptionType.FORBIDDEN_ACCESS_CART_DATA);
        }
        int result = cartMenuDao.updateCartMenuQuantity(id, cartUpdateReq.getQuantity());
        return result;
    }

    // 카트 삭제하기 api
    public int deleteCartApi(int id){
        // 토큰의 아이디와 카트 테이블의 userId 비교
        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"),"id");
        String cartUserId = cartMenuDao.getCartMenuByCartId(id).getUserId();
        if(!userId.equals(cartUserId)){
            // 아이디 일치하지 않을 경우
            throw new CartException(CartExceptionType.FORBIDDEN_ACCESS_CART_DATA);
        }
        //카트에 담긴 정보 삭제하기
        int result = cartMenuDao.deleteCartMenu(id);
        return result;
    }


    // DB의 옵션 문자열을 변환
    public String convertOptionStringToCli(String option){
        //메뉴 옵션 "1__1/4__2" => "HOT/샷추가(2개)" 로 바꾸는 작업
        StringBuilder convert = new StringBuilder();
        String[] arrOption = option.split("/");
        for (String opStr : arrOption) {
            String[] oneOption = opStr.split("__");
            Option tmpOption = optionDao.getOptionById(Integer.parseInt(oneOption[0]));

            if (tmpOption.getType().equals("Temperature")) {
                convert.append(tmpOption.getName());
            } else if(tmpOption.getType().equals("Shot")){
                convert.append("/샷추가("+oneOption[1]+"개)");
            } else if(tmpOption.getType().equals("Syrup")){
                convert.append("/시럽추가("+oneOption[1]+"개)");
            } else if(tmpOption.getType().equals("Size")){
                convert.append("/사이즈업");
            }
        }
        return convert.toString();
    }
}
