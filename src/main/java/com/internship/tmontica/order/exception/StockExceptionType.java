package com.internship.tmontica.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum StockExceptionType {
    NOT_ENOUGH_STOCK("menuId", "재고가 부족합니다", HttpStatus.BAD_REQUEST);

    private String field;
    private String errorMessage;
    private HttpStatus responseType;
}
