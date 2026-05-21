package com.my.ox_quiz.controller;


import com.my.ox_quiz.dto.MemberDto;
import com.my.ox_quiz.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final MemberService memberService;

    @GetMapping("/admin/members")
    public String memberList(Model model){
        List<MemberDto> members = memberService.findAllMembers();
        model.addAttribute("members",members);
        return "admin/member-list";
    }

    @PostMapping("/admin/member/approve")
    public String approveMember(@RequestParam String id){
        memberService.approveMember(id);
        return "redirect:/admin/members";
    }
    @PostMapping("/admin/member/password")
    public String forceChangePassword(@RequestParam String id, @RequestParam String password){
        memberService.changePassword(id, password);
        return "redirect:/admin/members";
    }
}
