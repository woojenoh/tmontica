package com.internship.tmontica.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionsResp {
    private int id;
    private String type;
    private String name;
    private int price;
}