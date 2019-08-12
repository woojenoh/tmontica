package com.internship.tmontica.cart.exception;

import lombok.Getter;

@Getter
public class CartException extends RuntimeException{
    private String field;
    private String message;
    private CartExceptionType cartExceptionType;

    public CartException(CartExceptionType cartExceptionType){
        this.field = cartExceptionType.getField();
        this.message = cartExceptionType.getMessage();
        this.cartExceptionType = cartExceptionType;
    }
}
