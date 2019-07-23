package com.internship.tmontica.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CartMenu {
    @NotNull
    private int quantity;

    private String option;
    private int id;

    @NotNull
    private String userId;

    private int price;  // 메뉴의 옵션, 수량이 모두 포함된 가격

    @NotNull
    private int menuId;
    @NotNull
    private boolean direct;

    public CartMenu(@NotNull int quantity, String option, @NotNull String userId, int price, @NotNull int menuId, @NotNull boolean direct) {
        this.quantity = quantity;
        this.option = option;
        this.userId = userId;
        this.price = price;
        this.menuId = menuId;
        this.direct = direct;
    }
}
