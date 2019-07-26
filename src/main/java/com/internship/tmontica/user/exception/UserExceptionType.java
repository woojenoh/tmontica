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
    MISSING_SESSION_ATTRIBUTE_EXCEPTION("sessionAttribute", "세션 어트리뷰트를 찾지 못했습니다.", HttpStatus.UNAUTHORIZED),
    PASSWORD_MISMATCH_EXCEPTION("password", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_ID_DUPLICATED_EXCEPTION("userId", "중복된 아이디 입니다.", HttpStatus.CONFLICT),
    USER_ID_NOT_FOUND_EXCEPTION("userId", "아이디를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private String field;
    private String errorMessage;
    private HttpStatus responseType;
}
