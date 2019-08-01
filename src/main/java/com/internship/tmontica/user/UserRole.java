package com.internship.tmontica.user;

import lombok.Getter;

@Getter
public enum UserRole {

    USER("USER"),
    SILVER_USER("SILVER"),
    GOLD_USER("GOLD"),
    VIP_USER("VIP"),
    VVIP_USER("VVIP"),
    ADMINISTRATOR("ADMIN");

    private String role;

    UserRole(String role){

        this.role = role;
    }
}


