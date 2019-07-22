package com.internship.tmontica.util;

import lombok.Getter;

@Getter
public class TmonTicaExceptionFormat {

    private String field;
    private String exceptionMessage;

    public TmonTicaExceptionFormat(String field, String exceptionMessage) {
        this.field = field;
        this.exceptionMessage = exceptionMessage;
    }
}
