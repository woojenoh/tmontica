package com.internship.tmontica.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryName {

    CATEGORY_MONTHLY("monthlymenu", "이달의 메뉴"),
    CATEGORY_COFFEE("coffee", "커피"),
    CATEGORY_ADE("ade", "에이드"),
    CATEGORY_BREAD("bread", "빵");

    private String categoryEng;
    private String categoryKo;

    public static String convertEngToKo(String categoryEng){

        for(CategoryName categoryName : CategoryName.values()){
            if(categoryName.getCategoryEng().equals(categoryEng)){
                return categoryName.getCategoryKo();
            }
        }

        return "non";
    }

}
