package com.internship.tmontica.point;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Point {

    @NotNull
    private String userId;
    private int id;
    @NotNull
    private String type;
    @NotNull
    private Date date;
    @NotNull
    @Min(0)
    private int amount;
    private String description;
}
