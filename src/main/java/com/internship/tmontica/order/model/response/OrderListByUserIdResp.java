package com.internship.tmontica.order.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListByUserIdResp {

    private int totalCnt;
    private List<OrderListResp> orders;
}
