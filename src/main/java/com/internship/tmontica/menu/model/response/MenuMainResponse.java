package com.internship.tmontica.menu.model.response;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuMainResponse {
    private String categoryKo;
    private String categoryEng;
    private List<MenuSimpleResponse> menus = new ArrayList<>();

    public MenuMainResponse(String categoryEng, String categoryKo){
        this.categoryEng = categoryEng;
        this.categoryKo = categoryKo;
    }
}
