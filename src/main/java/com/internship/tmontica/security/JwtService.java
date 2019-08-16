package com.internship.tmontica.security;

import com.internship.tmontica.user.model.response.UserTokenInfoDTO;

public interface JwtService {

    String getToken(UserTokenInfoDTO userTokenInfoDTO);
    boolean isUsable(String jwt);
    String getUserInfo(String key);
}
