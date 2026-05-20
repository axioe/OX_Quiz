package com.my.ox_quiz.config;

import com.my.ox_quiz.interceptor.AdminCheckInterceptor;
import com.my.ox_quiz.interceptor.ApprovalCheckInterceptor;
import com.my.ox_quiz.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/member/my-page","/quiz/**","/admin/**")
                .excludePathPatterns("/member/join","/member/login","/member/logout");

        registry.addInterceptor(new AdminCheckInterceptor())
                .addPathPatterns("/quiz/**","/admin/**")
                .excludePathPatterns("/quiz/play","/quiz/check");

        registry.addInterceptor(new ApprovalCheckInterceptor())
                .addPathPatterns("/quiz/play","/quiz/check");
    }
}
