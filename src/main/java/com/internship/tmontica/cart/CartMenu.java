package com.internship.tmontica.cart;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CartMenu {
    @NotNull
    @Min(1)
    private int quantity;

    private String option;
    private int id;

    @NotEmpty
    private String userId;
    @NotNull
    private int price;  // 옵션의 가격...
    @NotNull
    private int menuId;
    @NotNull
    private boolean direct;

    public CartMenu(@NotNull int quantity, String option, @NotEmpty String userId, @NotNull int price, @NotNull int menuId, @NotNull boolean direct) {
        this.quantity = quantity;
        this.option = option;
        this.userId = userId;
        this.price = price;
        this.menuId = menuId;
        this.direct = direct;
    }
}
