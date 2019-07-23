package com.internship.tmontica.config;
import com.internship.tmontica.security.JwtInterceptor;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.util.UserConfigValueName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtService jwtService;
    private static final String[] EXCLUDE_PATH = {
            "/api/users/signin", "/api/users/signup", "/api/users/duplicate/**",
            "/api/users/findid/*", "/api/users/findpw","/swagger*/**", "/resources/**"
            , "/**/*.jpg", "/**/*.js", "/**/*.css", "/error/**"
    };
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 uri에 대해 http://localhost:18080, http://localhost:8180 도메인은 접근을 허용한다.
        registry.addMapping("/**")
                .allowedOrigins("*") //http://localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders(UserConfigValueName.JWT_TOKEN_HEADER_KEY);
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor(jwtService))
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATH);
    }
}
