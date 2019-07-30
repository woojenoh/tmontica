package com.internship.tmontica.exception.handler;

import com.internship.tmontica.exception.TmonTicaExceptionFormat;
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
        switch (e.getUserExceptionType().getResponseType()) {

            case BAD_REQUEST:
                return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getErrorMessage()), HttpStatus.BAD_REQUEST);
            case NOT_FOUND:
                return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getErrorMessage()), HttpStatus.NOT_FOUND);
            case CONFLICT:
                return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getErrorMessage()), HttpStatus.CONFLICT);
            case UNAUTHORIZED:
                return new ResponseEntity<>(new TmonTicaExceptionFormat(e.getField(), e.getErrorMessage()), HttpStatus.UNAUTHORIZED);

            default:
                return new ResponseEntity<>(new TmonTicaExceptionFormat("유저핸들러 케이스", "분류되지않은 케이스"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(MailSendException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public TmonTicaExceptionFormat handleEmailSendException(){
        log.info("fail to send email");
        return new TmonTicaExceptionFormat("email sending", "이메일 전송 실패.");
    }
}
