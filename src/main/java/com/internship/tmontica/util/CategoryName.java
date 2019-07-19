package com.internship.tmontica.util;

public final class CategoryName {
    public static final String CATEGORY_COFFEE = "coffee";
    public static final String CATEGORY_ADE = "ade";
    public static final String CATEGORY_DESSERT = "dessert";

    public static final String CATEGORY_COFFEE_KO = "커피";
    public static final String CATEGORY_ADE_KO = "에이드";
    public static final String CATEGORY_DESSERT_KO = "디저트";

    public static String categoryEngToKo(String categoryEng){
        if(categoryEng.equals(CATEGORY_COFFEE)) return CATEGORY_COFFEE_KO;
        else if(categoryEng.equals(CATEGORY_ADE)) return CATEGORY_ADE_KO;
        else if(categoryEng.equals(CATEGORY_DESSERT)) return CATEGORY_DESSERT_KO;
        return "NON";
    }
}
