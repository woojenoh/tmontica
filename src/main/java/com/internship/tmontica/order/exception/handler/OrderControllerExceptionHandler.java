package com.internship.tmontica.order.exception.handler;

import com.internship.tmontica.order.exception.NotEnoughStockException;
import com.internship.tmontica.security.exception.UnauthorizedException;
import com.internship.tmontica.exception.TmonTicaExceptionFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderControllerExceptionHandler{
    private static final Logger log = LoggerFactory.getLogger(OrderControllerExceptionHandler.class);

    @ExceptionHandler(NotEnoughStockException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    public TmonTicaExceptionFormat handleUnauthorizedException(UnauthorizedException e) {

        log.info("stock is not enough exception");
        return new TmonTicaExceptionFormat("menu stock", "메뉴의 재고가 부족합니다");
    }
}
