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

    private int optionPrice;

    @NotNull
    private int menuId;
    @NotNull
    private boolean direct;

    public CartMenu(@NotNull int quantity, String option, @NotNull String userId, int optionPrice, @NotNull int menuId, @NotNull boolean direct) {
        this.quantity = quantity;
        this.option = option;
        this.userId = userId;
        this.optionPrice = optionPrice;
        this.menuId = menuId;
        this.direct = direct;
    }
}
