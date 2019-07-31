package com.internship.tmontica.order.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class OrderValidException extends RuntimeException{
    private String field;
    private String exceptionMessage;
    private OrderExceptionType orderExceptionType;
    private BindingResult bindingResult;

    public OrderValidException(OrderExceptionType orderExceptionType, BindingResult bindingResult){
        this.field = orderExceptionType.getField();
        this.exceptionMessage = orderExceptionType.getErrorMessage();
        this.orderExceptionType = orderExceptionType;
        this.bindingResult = bindingResult;
    }
}
