package com.internship.tmontica.menu.model.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryResponse {
    private String categoryKo;
    private String categoryEng;
    private int size;
    private int page;
    private List<MenuSimpleResponse> menus = new ArrayList<>();

    public MenuCategoryResponse(int size, int page, String categoryEng, String categoryKo){
        this.size = size;
        this.page = page;
        this.categoryEng = categoryEng;
        this.categoryKo = categoryKo;
    }

}
