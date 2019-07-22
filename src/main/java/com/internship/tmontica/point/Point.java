package com.internship.tmontica.point;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Alias("point")
@Data
public class Point {

    private String userId;
    private int id;
    private String type;
    private Date date;
    private int amount;
    private String description;
}
