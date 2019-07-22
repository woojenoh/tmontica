package com.internship.tmontica.security;

import com.internship.tmontica.user.User;

import java.util.Map;

public interface JwtService {

    String getToken(User user);
    boolean isUsable(String jwt);
}
