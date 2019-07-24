package com.internship.tmontica.point;

import lombok.Data;

import java.util.Date;

@Data
public class Point {

    private String userId;
    private int id;
    private String type;
    private Date date;
    private int amount;
    private String description;
}
