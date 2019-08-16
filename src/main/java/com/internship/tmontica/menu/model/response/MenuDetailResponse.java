package com.internship.tmontica.menu.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDetailResponse {
    private int id;
    private String nameEng;
    private String nameKo;
    private String description;
    private String imgUrl;
    private int sellPrice;
    private int productPrice;
    private int discountRate;
    private String categoryEng;
    private String categoryKo;
    private Date startDate;
    private Date endDate;
    private boolean usable;
    private int stock;
    private boolean monthlyMenu;
    private List<MenuOptionResponse> option;

    public void setImgUrl(String imgUrl){
        this.imgUrl = "/images/".concat(imgUrl);
    }
}