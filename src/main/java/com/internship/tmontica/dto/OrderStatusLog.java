package com.internship.tmontica.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Alias("orderStatusLog")
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
