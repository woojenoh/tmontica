package com.internship.tmontica.user.exception.handler;

import lombok.Getter;

@Getter
public class TmonTicaException {

    private String field;
    private String exceptionMessage;

    public TmonTicaException(String field, String exceptionMessage) {
        this.field = field;
        this.exceptionMessage = exceptionMessage;
    }
}
