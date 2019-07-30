package com.internship.tmontica.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class TmonTicaExceptionFormat {

    private String field;
    private String exceptionMessage;
    private List<FieldError> errors;

    public TmonTicaExceptionFormat(String field, String exceptionMessage) {
        this.field = field;
        this.exceptionMessage = exceptionMessage;
    }

    public TmonTicaExceptionFormat(String field, String exceptionMessage, List<FieldError> errors){
        this.field = field;
        this.exceptionMessage = exceptionMessage;
        this.errors = errors;
    }

    @Getter
    @NoArgsConstructor
    public static class FieldError{
        private String field;
        private String value;
        private String reason;
    }
}
