package com.internship.tmontica.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDetailResp {
    private int id;
    private String nameEng;
    private String nameKo;
    private String description;
    private String imgUrl;
    private int sellPrice;
    private int discountRate;
    private String category; // 영어이름, 한글이름 나눠야 함
    private int stock;
    private boolean monthlyMenu;
    private List options;
}