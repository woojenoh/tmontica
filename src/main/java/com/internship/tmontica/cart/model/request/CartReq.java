package com.internship.tmontica.cart.model.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartReq {

    @Min(1)
    private int menuId;
    @Min(1)
    private int quantity;
    @Valid
    private List<Cart_OptionReq> option;
    @NotNull
    private Boolean direct; // @NotNull 적용을 위해
}
