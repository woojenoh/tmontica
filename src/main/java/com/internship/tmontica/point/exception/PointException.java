package com.internship.tmontica.point.exception;

import lombok.Getter;

@Getter
public class PointException  extends RuntimeException{

    private String field;
    private String errorMessage;
    private PointExceptionType pointExceptionType;

    public PointException(PointExceptionType pointExceptionType){
        this.field = pointExceptionType.getField();
        this.errorMessage = pointExceptionType.getErrorMessage();
        this.pointExceptionType = pointExceptionType;
    }

}
