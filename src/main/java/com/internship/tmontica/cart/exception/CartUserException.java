package com.internship.tmontica.cart.exception;

import lombok.Getter;

@Getter
public class CartUserException extends RuntimeException{
    private String field;
    private String exceptionMessage;
    private CartExceptionType cartExceptionType;

    public CartUserException(CartExceptionType cartExceptionType){
        this.field = cartExceptionType.getField();
        this.exceptionMessage = cartExceptionType.getErrorMessage();
        this.cartExceptionType = cartExceptionType;
    }
}
