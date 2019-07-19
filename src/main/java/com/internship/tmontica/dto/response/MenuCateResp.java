package com.internship.tmontica.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCateResp {
    private int size;
    private List<MenuSimpleResp> menus = new ArrayList<>();
}
