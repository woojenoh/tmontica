package com.internship.tmontica.user;

import lombok.Getter;

@Getter
public enum MailType {

    FIND_ID("아이디 찾기 이메일 입니다."),
    FIND_PW("비밀번호 찾기 이메일 입니다."),
    SIGN_UP("회원가입 인증 이메일 입니다.");

    private String description;

    MailType(String description){
        this.description = description;
    }
}
