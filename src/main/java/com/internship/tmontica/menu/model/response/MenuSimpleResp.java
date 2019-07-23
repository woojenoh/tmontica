package com.internship.tmontica.menu.model.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuSimpleResp {
    private int id;
    private String nameEng;
    private String nameKo;
    private String img;
    private int stock;
}
