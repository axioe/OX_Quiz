package com.my.ox_quiz.interceptor;

import com.my.ox_quiz.domain.Member;
import com.my.ox_quiz.domain.MemberStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class ApprovalCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session != null){
            Member loginMember = (Member) session.getAttribute("loginMember");

            if (loginMember !=null && loginMember.getStatus() == MemberStatus.PENDING){
                response.sendRedirect(request.getContextPath()+"/member/my-page");
                return false;
            }
        }
        return true;
    }
}
