package com.internship.tmontica.dto;

import lombok.Data;

@Data
public class CartMenu {
    private int quantity;
    private String option;
    private int id;
    private String userId;
    private int optionPrice;
    private int menuId;
}
