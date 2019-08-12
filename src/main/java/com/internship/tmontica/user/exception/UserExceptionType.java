package com.internship.tmontica.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserExceptionType {

    EMAIL_MISMATCH_EXCEPTION("email", "이메일이 일치하지 않습니다.", HttpStatus.NOT_FOUND),
    INVALID_OPTION_EXCEPTION("option", "지원하지 않는 옵션을 요청했습니다.", HttpStatus.BAD_REQUEST),
    INVALID_USER_ID_EXCEPTION("userId", "부적절한 유저 아이디 입니다.", HttpStatus.BAD_REQUEST),
    INVALID_USER_ROLE_EXCEPTION("userRole","부적절한 유저 권한 입니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH_EXCEPTION("password", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_ID_DUPLICATED_EXCEPTION("userId", "중복된 아이디 입니다.", HttpStatus.CONFLICT),
    USER_ID_NOT_FOUND_EXCEPTION("userId", "아이디를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_NOT_ACTIVATE_EXCEPTION("isActive", "비활성화된 유저 입니다.", HttpStatus.UNAUTHORIZED),
    ACTIVATE_CODE_MISMATCH_EXCEPTION("activeCode", "활성화 코드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    AUTHCODE_NOT_FOUND_EXCEPTION("authCode", "인증 코드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    DATABASE_FAIL_EXCEPTION("dataBase", "잠시후 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_NOT_FOUND_EXCEPTION("email", "가입되지 않는 이메일 주소 입니다.", HttpStatus.BAD_REQUEST);

    private String field;
    private String message;
    private HttpStatus responseType;
}
