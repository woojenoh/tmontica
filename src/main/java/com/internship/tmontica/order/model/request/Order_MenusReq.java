package com.internship.tmontica.order.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class Order_MenusReq {

    @Min(1)
    private int cartId;

}
