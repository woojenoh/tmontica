package com.internship.tmontica.menu;

import com.internship.tmontica.menu.exception.MenuException;
import com.internship.tmontica.menu.exception.MenuExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryName {

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

        throw new MenuException(MenuExceptionType.CATEGORY_NAME_MISMATCH_EXCEPTION);
    }

}
