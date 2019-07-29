package com.internship.tmontica.util;

public final class CategoryName {
    public static final String CATEGORY_COFFEE = "coffee";
    public static final String CATEGORY_ADE = "ade";
    public static final String CATEGORY_BREAD = "bread";
    public static final String CATEGORY_MONTHLY = "monthlymenu";

    public static final String CATEGORY_COFFEE_KO = "커피";
    public static final String CATEGORY_ADE_KO = "에이드";
    public static final String CATEGORY_BREAD_KO = "빵";
    public static final String CATEGORY_MONTHLY_KO = "이달의 메뉴";

    public static String categoryEngToKo(String categoryEng){
        if(categoryEng.equals(CATEGORY_COFFEE)) return CATEGORY_COFFEE_KO;
        else if(categoryEng.equals(CATEGORY_ADE)) return CATEGORY_ADE_KO;
        else if(categoryEng.equals(CATEGORY_BREAD)) return CATEGORY_BREAD_KO;
        else if(categoryEng.equals(CATEGORY_MONTHLY)) return CATEGORY_MONTHLY_KO;
        return "NON";
    }
}
