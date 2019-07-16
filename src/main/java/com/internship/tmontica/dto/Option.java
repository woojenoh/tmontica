package com.internship.tmontica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("option")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    private int id;
    private String name;
    private String type;
    private int price;

    public Option(String name , String type, int price){
        this.name = name;
        this.type = type;
        this.price = price;
    }
}
