package com.internship.tmontica.cart.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class CartValidException extends RuntimeException{
    private String field;
    private String exceptionMessage;
    private CartExceptionType cartExceptionType;
    private BindingResult bindingResult;

    public CartValidException(CartExceptionType cartExceptionType, BindingResult bindingResult){
        this.field = cartExceptionType.getField();
        this.exceptionMessage = cartExceptionType.getErrorMessage();
        this.cartExceptionType = cartExceptionType;
        this.bindingResult = bindingResult;
    }
}
