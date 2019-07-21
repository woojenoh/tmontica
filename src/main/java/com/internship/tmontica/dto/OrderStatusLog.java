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
    private String orderId;
    @NotNull
    private Date modifiedDate;

    public OrderStatusLog(@NotNull String statusType, @NotNull String editorId, @NotNull String orderId) {
        this.statusType = statusType;
        this.editorId = editorId;
        this.orderId = orderId;
    }
}
