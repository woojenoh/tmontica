package com.internship.tmontica.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;

@Alias("orderDetail")
@Data
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

}
