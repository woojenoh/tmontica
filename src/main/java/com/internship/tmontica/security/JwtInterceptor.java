package com.internship.tmontica.security;

import com.internship.tmontica.security.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
<<<<<<< HEAD
        final String token = request.getHeader("Authorization");
=======
>>>>>>> feature/be-user

//        if(token != null && jwtService.isUsable(token)){
//            return true;
//        }
//
//        throw new UnauthorizedException();

        return token != null && jwtService.isUsable(token);
    }
}
