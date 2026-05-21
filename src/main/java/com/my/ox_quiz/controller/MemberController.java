package com.my.ox_quiz.controller;

import com.my.ox_quiz.domain.Member;
import com.my.ox_quiz.domain.RoleType;
import com.my.ox_quiz.dto.MemberDto;
import com.my.ox_quiz.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/join")
    public String joinForm() {
        return "member/join";
    }

    @PostMapping("/member/join")
    public String join(@ModelAttribute MemberDto memberDto, Model model) {
        try {
            memberService.join(memberDto);
            return "redirect:/member/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/join";
        }
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/member/login")
    public String login(@RequestParam String id, @RequestParam String password,
                        HttpServletRequest request, Model model) {
        try {
            Member loginMember = memberService.login(id, password);


            HttpSession session = request.getSession();
            session.setAttribute("loginMember", loginMember);


            if (loginMember.getRole() == RoleType.ADMIN) {
                return "redirect:/quiz"; // 관리자면 /quiz 이동
            } else {
                return "redirect:/member/my-page";
            }

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/login";
        }
    }

    @GetMapping("/member/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/member/my-page")
    public String myPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute("loginMember");


        MemberDto currentMember = memberService.findMemberById(sessionMember.getId());
        model.addAttribute("member", currentMember);


        model.addAttribute("statusMessage", currentMember.getStatus().getMessage());

        return "member/my-page";
    }

    @PostMapping("/member/password")
    public String changePassword(@RequestParam String password, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute("loginMember");

        try {

            memberService.changePassword(sessionMember.getId(), password);
            return "redirect:/member/my-page";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/my-page";
        }
    }
}