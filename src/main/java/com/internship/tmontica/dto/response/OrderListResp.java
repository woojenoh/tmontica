package com.internship.tmontica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListResp {
    private int orderID;
    private Date orderDate;
    private String status;
    private List<String> menuNames;
}
