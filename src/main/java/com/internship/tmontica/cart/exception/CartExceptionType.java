package com.internship.tmontica.cart.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CartExceptionType {
    INVALID_CART_ADD_FORM("cart add form", "카트 추가 폼이 올바르지 않습니다", HttpStatus.BAD_REQUEST),
    INVALID_CART_UPDATE_FORM("cart update form", "카트 수정 폼이 올바르지 않습니다", HttpStatus.BAD_REQUEST);

    private String field;
    private String errorMessage;
    private HttpStatus responseType;
}
