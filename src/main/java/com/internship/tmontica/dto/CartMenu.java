package com.internship.tmontica.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartMenu {
    private int quantity;
    private String option;
    private int id;
    private String userId;
    private int optionPrice;
    private int menuId;

    public CartMenu(int quantity, String option, String userId, int optionPrice, int menuId) {
        this.quantity = quantity;
        this.option = option;
        this.userId = userId;
        this.optionPrice = optionPrice;
        this.menuId = menuId;
    }
}
