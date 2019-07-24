package com.internship.tmontica.security;

import com.internship.tmontica.user.User;
import com.internship.tmontica.user.model.response.UserTokenInfoDTO;

import java.util.Map;

public interface JwtService {

    String getToken(UserTokenInfoDTO userTokenInfoDTO);
    boolean isUsable(String jwt);
    String getUserInfo(String key);
}
