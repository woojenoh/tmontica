package com.internship.tmontica.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MenuCategoryResp {
    private String categoryKo;
    private String categoryEng;
    private List<MenuSimpleResp> menus = new ArrayList<>();
}
