package com.internship.tmontica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Alias("option")
@Data
public class Option {
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @Min(0)
    private int price;

    public Option(String name , String type, int price){
        this.name = name;
        this.type = type;
        this.price = price;
    }
}
