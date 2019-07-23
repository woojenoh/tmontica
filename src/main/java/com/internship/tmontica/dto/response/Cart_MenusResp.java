package com.internship.tmontica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart_MenusResp {
    private int cartId;
    private int menuId;
    private String nameEng;
    private String nameKo;
    private String imgUrl;
    private String option;
    private int quantity;
    private int price;
    private int stock;
}
