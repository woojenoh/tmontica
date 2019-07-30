package com.internship.tmontica.order.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderStatusReq {
    @NotEmpty
    private String status;
}
