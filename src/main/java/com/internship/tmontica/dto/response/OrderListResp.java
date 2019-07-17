package com.internship.tmontica.dto.response;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class OrderListResp {
    private int orderID;
    private Date orderDate;
    private String status;
    private List<String> menuNames;
}
