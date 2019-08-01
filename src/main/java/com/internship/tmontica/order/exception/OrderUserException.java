package com.internship.tmontica.order.exception;

import lombok.Getter;

@Getter
public class OrderUserException extends RuntimeException{
    private String field;
    private String exceptionMessage;
    private OrderExceptionType orderExceptionType;

    public OrderUserException(OrderExceptionType orderExceptionType){
        this.field = orderExceptionType.getField();
        this.exceptionMessage = orderExceptionType.getErrorMessage();
        this.orderExceptionType = orderExceptionType;
    }
}
