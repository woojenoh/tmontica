package com.internship.tmontica.menu.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum MenuExceptionType {

    MENU_NO_CONTENT_EXCEPTION("menu" , "메뉴가 존재하지 않습니다." , HttpStatus.OK);

    private String field;
    private String errorMessage;
    private HttpStatus responseType;
}
