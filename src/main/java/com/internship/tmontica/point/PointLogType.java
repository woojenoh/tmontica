package com.internship.tmontica.point;

import lombok.Getter;

@Getter
public enum PointLogType {

    USE_POINT("USE"),
    GET_POINT("GET"),
    DISSIPATE_POINT("DISSIPATE");

    private String type;

    PointLogType(String type){
        this.type = type;
    }
}
