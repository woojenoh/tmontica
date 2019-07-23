package com.internship.tmontica.menu.model.response;
import com.internship.tmontica.option.Option;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDetailResp {
    private int id;
    private String nameEng;
    private String nameKo;
    private String description;
    private String img;
    private int sellPrice;
    private int discountRate;
    private String categoryEng;
    private String categoryKo;
    private int stock;
    private boolean monthlyMenu;
    private List<Option> option;
}