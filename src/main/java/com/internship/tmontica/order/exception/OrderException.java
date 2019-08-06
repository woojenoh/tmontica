package com.internship.tmontica.order.exception;

import lombok.Getter;

@Getter
public class OrderException extends RuntimeException{
    private String field;
    private String message;
    private OrderExceptionType orderExceptionType;

    public OrderException(OrderExceptionType orderExceptionType){
        this.field = orderExceptionType.getField();
        this.message = orderExceptionType.getMessage();
        this.orderExceptionType = orderExceptionType;
    }
}
