package com.internship.tmontica.exception.handler;

import com.internship.tmontica.cart.exception.CartValidException;
import com.internship.tmontica.exception.TmonTicaExceptionFormat;
import com.internship.tmontica.menu.exception.MenuException;
import com.internship.tmontica.menu.exception.SaveImgException;
import com.internship.tmontica.menu.exception.MenuValidException;
import com.internship.tmontica.order.exception.NotEnoughStockException;
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
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public TmonTicaExceptionFormat handleUnauthorizedException(UnauthorizedException e) {

        log.info("need authorization");
        return new TmonTicaExceptionFormat("authorization", "권한이 유효하지 않습니다.");
    }

    // 유저
    @ExceptionHandler(UserValidException.class)
    public TmonTicaExceptionFormat handleUserValidException(UserValidException e) {
        log.info("UserValidExceptionMessage : {}" , e.getMessage());
        return new TmonTicaExceptionFormat(e.getField(), e.getErrorMessage(), e.getBindingResult());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<TmonTicaExceptionFormat> handleUserException(UserException e) {
        log.debug("UserExceptionMessage : {}", e.getErrorMessage());
        return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getErrorMessage()), e.getUserExceptionType().getResponseType());
    }

    // 메일
    @ExceptionHandler(MailSendException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public TmonTicaExceptionFormat handleEmailSendException(MailSendException e){
        log.info("fail to send email : {}", e.getMessage());
        return new TmonTicaExceptionFormat("email sending", e.getMessage());
    }

    // 메뉴
    @ExceptionHandler(MenuValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TmonTicaExceptionFormat handleMenuValidException(MenuValidException e) {
        log.info("MenuExceptionMessage : {}" , e.getMessage());
        return new TmonTicaExceptionFormat(e.getField(), e.getExceptionMessage(), e.getBindingResult());
    }

    @ExceptionHandler(SaveImgException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TmonTicaExceptionFormat handleSaveImgException(SaveImgException e){
        return new TmonTicaExceptionFormat("imgFile", "올바른 이미지 파일이 아닙니다.");
    }

    @ExceptionHandler(MenuException.class)
    public ResponseEntity<TmonTicaExceptionFormat> handleMenuException(MenuException e){
        log.info("MenuException : {}" , e.getErrorMessage());
        return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getErrorMessage()), e.getMenuExceptionType().getResponseType());
    }

    // 카트
    @ExceptionHandler(CartValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TmonTicaExceptionFormat handleCartValidException(CartValidException e){
        return new TmonTicaExceptionFormat(e.getField(), e.getExceptionMessage(), e.getBindingResult());
    }

    // 재고
    @ExceptionHandler(NotEnoughStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TmonTicaExceptionFormat handleNotEnoughStockException(NotEnoughStockException e) {
        log.info("stock is not enough exception");
        return new TmonTicaExceptionFormat(e.getField(), e.getExceptionMessage());
    }
}
