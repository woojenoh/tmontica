package com.internship.tmontica.user.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private String field;
    private String message;
    private UserExceptionType userExceptionType;

    public UserException(UserExceptionType userExceptionType){
        this.field = userExceptionType.getField();
        this.message = userExceptionType.getMessage();
        this.userExceptionType = userExceptionType;
    }
}
