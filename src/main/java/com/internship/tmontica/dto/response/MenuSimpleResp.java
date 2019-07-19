package com.internship.tmontica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenuSimpleResp {
    private int id;
    private String nameEng;
    private String nameKo;
    private String imgUrl;
}
