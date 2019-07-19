package com.internship.tmontica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Alias("menu")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  Menu {
    private int id;
    @NotEmpty
    private String nameEng;
    @Min(0)
    private int productPrice;
    @NotEmpty
    private String categoryKo;
    @NotEmpty
    private String categoryEng;
    private boolean monthlyMenu;
    private boolean usable;
    @NotEmpty
    private String img;
    private String description;
    @Min(0)
    private int sellPrice;
    @Min(0)
    private int discountRate;
    private Date createdDate;
    private Date updatedDate;
    private String creatorId;
    private String updaterId;

    @Min(0)
    private int stock;
    @NotEmpty
    private String nameKo;

    // 메뉴의 옵션
    private List<Option> options;

    public void addOption(Option option){
        if(options == null)
            options = new ArrayList<>();
        options.add(option);
    }

}
