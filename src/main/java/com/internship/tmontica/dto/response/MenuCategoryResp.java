package com.internship.tmontica.dto.response;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryResp {
    private String categoryKo;
    private String categoryEng;
    private List<MenuSimpleResp> menus = new ArrayList<>();
}
