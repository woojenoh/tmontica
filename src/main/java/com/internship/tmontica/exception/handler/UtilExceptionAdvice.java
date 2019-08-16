package com.internship.tmontica.exception.handler;

import com.internship.tmontica.exception.TmonTicaExceptionFormat;
import com.internship.tmontica.security.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UtilExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    // 권한
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<TmonTicaExceptionFormat> handleUnauthorizedException(UnauthorizedException e) {

        log.info("need authorization");
        return new ResponseEntity<>(new TmonTicaExceptionFormat("authorization", "권한이 유효하지 않습니다."), HttpStatus.UNAUTHORIZED);
    }
}
