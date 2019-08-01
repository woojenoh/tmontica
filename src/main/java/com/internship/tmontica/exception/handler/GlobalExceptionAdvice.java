package com.internship.tmontica.exception.handler;

import com.internship.tmontica.exception.TmonTicaExceptionFormat;
import com.internship.tmontica.menu.exception.MenuException;
import com.internship.tmontica.security.exception.UnauthorizedException;
import com.internship.tmontica.user.exception.UserException;
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

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public TmonTicaExceptionFormat handleUnauthorizedException(UnauthorizedException e) {

        log.info("need authorization");
        return new TmonTicaExceptionFormat("authorization", "권한이 유효하지 않습니다.");
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<TmonTicaExceptionFormat> handleUserException(UserException e) {
        log.debug("UserExceptionMessage : {}", e.getErrorMessage());
        return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getErrorMessage()), e.getUserExceptionType().getResponseType());
    }

    @ExceptionHandler(MailSendException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public TmonTicaExceptionFormat handleEmailSendException(){
        log.info("fail to send email");
        return new TmonTicaExceptionFormat("email sending", "이메일 전송 실패.");
    }

    // 메뉴
    @ExceptionHandler(MenuException.class)
    public ResponseEntity<TmonTicaExceptionFormat> handleMenuException(MenuException e){
        log.info("MenuException : {}" , e.getErrorMessage());
        return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getErrorMessage()), e.getMenuExceptionType().getResponseType());
    }
}
