package com.internship.tmontica.repository;

import com.internship.tmontica.dto.CartMenu;
import com.internship.tmontica.dto.OrderDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMenuDao {
    // 카트 아이디로 cart_menus 테이블에서 조회하기
    @Select("select * from cart_menus where id = #{cartId}")
    CartMenu getCartMenuByCartId(int cartId);

    // 카트에서 삭제하기
    @Delete("delete from cart_menus where id = #{cartId}")
    void deleteCartMenu(int cartId);

    // 카트에 추가하기
    @Insert("insert into cart_menus values(#{quantity}, #{option}, 0, #{userId}, #{price}, #{menuId}, #{direct})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addCartMenu(CartMenu cartMenu);

    // direct = true 인 카트메뉴 삭제하기
    @Delete("delete from cart_menus where direct = true")
    void deleteDirectCartMenu();

    // userId로 cart_menus 조회하기
    @Select("select * from cart_menus where user_id = #{userId}")
    List<CartMenu> getCartMenuByUserId(String userId);

    // 카트에서 수량과 가격 수정하기
    @Update("update cart_menus set quantity = #{quantity}, price = #{price} where id = #{id}")
    int updateCartMenuQuantity(int id, int price, int quantity);


}
