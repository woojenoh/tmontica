package com.internship.tmontica.user;

import lombok.Getter;

@Getter
public enum UserResponseMessage {

    SIGN_UP_SUCCESS("회원가입 성공. 활성화를 해야 로그인 하실 수 있습니다."),
    ACTIVATE_SUCCESS("인증완료. 계정이 활성화 되었습니다."),
    PASSWORD_CHECK_SUCCESS("비밀번호 일치."),
    PASSWORD_CHANGE_SUCCESS("비밀번호가 정상적으로 변경되었습니다."),
    WITHDRAW_USER_SUCCESS("회원 탈퇴가 성공적으로 이루어졌습니다."),
    MAIL_SEND_SUCCESS("이메일 전송 성공."),
    USABLE_ID("사용할 수 있는 아이디 입니다.");

    private String message;

    UserResponseMessage(String message) {
        this.message = message;
    }

}
