package com.internship.tmontica.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@NoArgsConstructor
public class OrderStatusLog {
    private int id;
    @NotNull
    private String statusType;
    @NotNull
    private String editorId;
    @NotNull
    private int orderId;
    @NotNull
    private Date modifiedDate;

    public OrderStatusLog(@NotNull String statusType, @NotNull String editorId, @NotNull int orderId) {
        this.statusType = statusType;
        this.editorId = editorId;
        this.orderId = orderId;
    }
}
