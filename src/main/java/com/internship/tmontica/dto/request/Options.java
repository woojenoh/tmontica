package com.internship.tmontica.dto.request;

import lombok.Data;

@Data
public class Options {
    private Temperature temperature;
    private Size size;
    private Shot shot;
    private Syrup syrup;
}
