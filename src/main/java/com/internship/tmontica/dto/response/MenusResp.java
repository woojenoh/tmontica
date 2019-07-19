package com.internship.tmontica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("menusResp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenusResp {
    private int menuId;
    private String nameEng;
    private String option;
    private int quantity;
    private int price;
    //api 명세 키값 바꾸도록 요청하기
}


