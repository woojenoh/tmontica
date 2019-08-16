package com.internship.tmontica.security;

import com.internship.tmontica.user.User;

public interface JwtService {

    String getToken(User user);
    boolean isUsable(String jwt);
    String getUserInfo(String key);
}
