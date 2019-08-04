package com.internship.tmontica.order.exception;

import lombok.Getter;

@Getter
public class NotEnoughStockException extends RuntimeException {
    private String field;
    private String message;
    private StockExceptionType stockExceptionType;

    public NotEnoughStockException(int menuId, StockExceptionType stockExceptionType){
        this.field = stockExceptionType.getField() + " : " + menuId;
        this.message = stockExceptionType.getMessage();
        this.stockExceptionType = stockExceptionType;
    }
}
