package com.internship.tmontica.cart.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartMenusResp {
    private int cartId;
    private int menuId;
    private String nameEng;
    private String nameKo;
    private String imgUrl;
    private String option;
    private int quantity;
    private int price; // 옵션+메뉴 가격(수량은 곱하지 않은)
    private int stock;
}
