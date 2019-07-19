package com.internship.tmontica.user.exception.handler;

import com.internship.tmontica.user.exception.InvalidUserIdException;
import com.internship.tmontica.user.exception.PasswordMismatchException;
import com.internship.tmontica.user.exception.UserIdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TmonTicaExceptionControllerAdvice {

        private static final Logger log = LoggerFactory.getLogger(TmonTicaExceptionControllerAdvice.class);

        @ExceptionHandler(InvalidUserIdException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public TmonTicaException handleInvalidUserIdException(InvalidUserIdException e) {

            log.info("invalid UserId Exception");
            return new TmonTicaException("userId", "사용 불가한 아이디 입니다.");
        }

        @ExceptionHandler(PasswordMismatchException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public TmonTicaException handlePasswordMisMatchException() {

            log.info("password mismatch Exception");
            return new TmonTicaException("password", "비밀번호가 일치하지 않습니다.");
        }

        @ExceptionHandler(UserIdNotFoundException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public TmonTicaException handleUserIdNotFoundException() {
            log.info("user id not found Exception");
            return new TmonTicaException("userId", "존재하지 않는 아이디입니다");
        }

}
