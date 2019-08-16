package com.internship.tmontica.menu.model.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuSimpleResponse {
    private int id;
    private String nameEng;
    private String nameKo;
    private String imgUrl;
    private int stock;

    public void setImgUrl(String imgUrl){
        this.imgUrl = "/images/".concat(imgUrl);
    }
}
