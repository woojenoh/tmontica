package com.internship.tmontica.user;

import lombok.Getter;

@Getter
public enum UserResponseMessage {

    SIGN_UP_FAIL("Sign up error"),
    SIGN_UP_SUCCESS("Sign up Success. Need Activate process."),
    ACTIVATE_SUCCESS("Account Activate Success"),
    ACTIVATE_FAIL("Fail to Activate"),
    PASSWORD_CHECK_SUCCESS("Correct Password"),
    PASSWORD_CHANGE_SUCCESS("Change password Success"),
    PASSWORD_CHANGE_FAIL("Change Password Fail"),
    WITHDRAW_USER_SUCCESS("Delete User Success"),
    WITHDRAW_USER_FAIL("Delete User Fail"),
    MAIL_SEND_SUCCESS("Send email Success"),
    MAIL_SEND_FAIL("Fail to send mail. Didnt exist user data or SMTP server error");

    private String message;

    UserResponseMessage(String message) {
        this.message = message;
    }

}
