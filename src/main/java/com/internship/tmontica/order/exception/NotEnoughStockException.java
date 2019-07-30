package com.internship.tmontica.order.exception;

import lombok.Getter;

@Getter
public class NotEnoughStockException extends RuntimeException {
    private String field;
    private String exceptionMessage;
    private StockExceptionType stockExceptionType;

    public NotEnoughStockException(int menuId, StockExceptionType stockExceptionType){
        this.field = stockExceptionType.getField() + " : " + menuId;
        this.exceptionMessage = stockExceptionType.getErrorMessage();
        this.stockExceptionType = stockExceptionType;
    }
}
