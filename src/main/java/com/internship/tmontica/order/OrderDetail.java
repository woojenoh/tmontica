package com.internship.tmontica.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class OrderDetail {

    private int id;
    @NotNull
    private int orderId;

    private String option;
    @NotNull
    private int price;
    @NotNull
    private int quantity;
    @NotNull
    private int menuId;

    public OrderDetail(int id, @NotNull int orderId, String option, @NotNull int price, @NotNull int quantity, @NotNull int menuId) {
        this.id = id;
        this.orderId = orderId;
        this.option = option;
        this.price = price;
        this.quantity = quantity;
        this.menuId = menuId;
    }
}
