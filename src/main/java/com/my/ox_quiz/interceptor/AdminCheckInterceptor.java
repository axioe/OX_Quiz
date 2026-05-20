package com.my.ox_quiz.interceptor;

import com.my.ox_quiz.domain.Member;
import com.my.ox_quiz.domain.RoleType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminCheckInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session != null){
            Member loginMember = (Member) session.getAttribute("loginMember");

            if (loginMember != null && loginMember.getRole() != RoleType.ADMIN){
                response.sendError(HttpServletResponse.SC_FORBIDDEN,"관리자만 접근할 수 있는 영역입니다.");
                return false;
            }
        }
        return true;
    }
}
