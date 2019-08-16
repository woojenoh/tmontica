package com.internship.tmontica.exception.handler;

import com.internship.tmontica.cart.exception.CartException;
import com.internship.tmontica.cart.exception.CartValidException;
import com.internship.tmontica.exception.TmonTicaExceptionFormat;
import com.internship.tmontica.menu.exception.MenuException;
import com.internship.tmontica.order.exception.NotEnoughStockException;
import com.internship.tmontica.order.exception.OrderException;
import com.internship.tmontica.order.exception.OrderValidException;
import com.internship.tmontica.point.exception.PointException;
import com.internship.tmontica.security.exception.UnauthorizedException;
import com.internship.tmontica.user.exception.UserException;
import com.internship.tmontica.user.exception.UserValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    // 권한
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<TmonTicaExceptionFormat> handleUnauthorizedException(UnauthorizedException e) {

        log.info("need authorization");
        return new ResponseEntity<>(new TmonTicaExceptionFormat("authorization", "권한이 유효하지 않습니다."), HttpStatus.UNAUTHORIZED);
    }

    // 유저
    @ExceptionHandler(UserValidException.class)
    public ResponseEntity<TmonTicaExceptionFormat> handleUserValidException(UserValidException e) {
        log.info("UserValidExceptionMessage : {}" , e.getMessage());
        return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getMessage(), e.getBindingResult()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<TmonTicaExceptionFormat> handleUserException(UserException e) {
        log.info("UserExceptionMessage : {}", e.getMessage());
        return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getMessage()), e.getUserExceptionType().getResponseType());
    }

    // 메일
    @ExceptionHandler(MailSendException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public TmonTicaExceptionFormat handleEmailSendException(MailSendException e){
        log.info("fail to send email : {}", e.getMessage());
        return new TmonTicaExceptionFormat("email sending", e.getMessage());
    }

    // 메뉴
    @ExceptionHandler(MenuException.class)
    public ResponseEntity<TmonTicaExceptionFormat> handleMenuException(MenuException e){
        log.info("MenuException : {}" , e.getMessage());
        return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getMessage()), e.getMenuExceptionType().getResponseType());
    }

    // 카트
    @ExceptionHandler(CartValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TmonTicaExceptionFormat handleCartValidException(CartValidException e){
        log.info("CartValidException : {}", e.getMessage());
        return new TmonTicaExceptionFormat(e.getField(), e.getMessage(), e.getBindingResult());
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity<TmonTicaExceptionFormat> handleCartUserException(CartException e){
        log.info("CartException : {}", e.getMessage());
        return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getMessage()), e.getCartExceptionType().getResponseType());
    }

    // 재고
    @ExceptionHandler(NotEnoughStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TmonTicaExceptionFormat handleNotEnoughStockException(NotEnoughStockException e) {
        log.info("stock is not enough exception");
        return new TmonTicaExceptionFormat(e.getField(), e.getMessage());
    }

    // 오더
    @ExceptionHandler(OrderValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TmonTicaExceptionFormat handleOrderValidException(OrderValidException e){
        log.info("OrderValidException : {}", e.getMessage());
        return new TmonTicaExceptionFormat(e.getField(), e.getMessage(), e.getBindingResult());
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<TmonTicaExceptionFormat> handleOrderUserException(OrderException e){
        log.info("OrderException : {}", e.getMessage());
        return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getMessage()), e.getOrderExceptionType().getResponseType());
    }

    // 포인트
    @ExceptionHandler(PointException.class)
    public ResponseEntity<TmonTicaExceptionFormat> handlePointException(PointException e) {
        log.info("PointExceptionMessage : {}", e.getMessage());
        return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getMessage()), e.getPointExceptionType().getResponseType());
    }

}
