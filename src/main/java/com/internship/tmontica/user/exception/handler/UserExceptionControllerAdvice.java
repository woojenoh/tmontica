package com.internship.tmontica.user.exception.handler;

import com.internship.tmontica.user.exception.*;
import com.internship.tmontica.util.TmonTicaExceptionFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionControllerAdvice {

        private static final Logger log = LoggerFactory.getLogger(UserExceptionControllerAdvice.class);

        @ExceptionHandler(InvalidUserIdException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public TmonTicaExceptionFormat handleInvalidUserIdException(InvalidUserIdException e) {

            log.info("invalid UserId Exception");
            return new TmonTicaExceptionFormat("userId", "사용 불가한 아이디 입니다.");
        }

        @ExceptionHandler(PasswordMismatchException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public TmonTicaExceptionFormat handlePasswordMisMatchException() {

            log.info("password mismatch Exception");
            return new TmonTicaExceptionFormat("password", "비밀번호가 일치하지 않습니다.");
        }

        @ExceptionHandler(UserIdNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public TmonTicaExceptionFormat handleUserIdNotFoundException() {
            log.info("user id not found Exception");
            return new TmonTicaExceptionFormat("userId", "존재하지 않는 아이디입니다.");
        }

        @ExceptionHandler(UserIdDuplicatedException.class)
        @ResponseStatus(HttpStatus.CONFLICT)
        public TmonTicaExceptionFormat handleUserIdDuplicatedException() {
            log.info("user id duplicated");
            return new TmonTicaExceptionFormat("userId", "중복된 아이디입니다.");
        }

        @ExceptionHandler(InvalidOptionException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public TmonTicaExceptionFormat handleInvalidOptionException() {
            log.info("invalid option request");
            return new TmonTicaExceptionFormat("option parameter", "구현되지 않은 옵션 파라미터 요청입니다.");
        }

        @ExceptionHandler(EmailMismatchException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public TmonTicaExceptionFormat handleEmailMismatchException() {
            log.info("email address mismatch");
            return new TmonTicaExceptionFormat("email info", "등록된 아이디의 이메일과 사용자가 입력한 이메일이 일치하지 않습니다.");
        }

        @ExceptionHandler(MailSendException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public TmonTicaExceptionFormat handleEmailSendException(){
            log.info("fail to send email");
            return new TmonTicaExceptionFormat("email sending", "이메일 전송 실패");
        }

}
