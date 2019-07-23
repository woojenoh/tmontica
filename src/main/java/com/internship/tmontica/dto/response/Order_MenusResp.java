package com.internship.tmontica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order_MenusResp {
    private int menuId;
    private String nameEng;
    private String nameKo;
    private String option;
    private String imgUrl;
    private int quantity;
    private int price;
}


