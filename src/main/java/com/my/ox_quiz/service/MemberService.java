package com.my.ox_quiz.service;

import com.my.ox_quiz.domain.Member;
import com.my.ox_quiz.domain.MemberStatus;
import com.my.ox_quiz.domain.RoleType;
import com.my.ox_quiz.dto.MemberDto;
import com.my.ox_quiz.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberDto dto) {
        memberRepository.findById(dto.getId()).ifPresent(m -> {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        });

        if (dto.getRole() == RoleType.ADMIN) {
            long adminCount = memberRepository.countByRole(RoleType.ADMIN);
            if (adminCount > 0) {
                throw new IllegalArgumentException("관리자 계정은 이미 시스템에 존재합니다. 추가 생성이 불가능합니다.");
            }
        }

        RoleType finalRole = dto.getRole() != null ? dto.getRole() : RoleType.USER;
        MemberStatus finalStatus = (finalRole == RoleType.ADMIN) ? MemberStatus.APPROVED : MemberStatus.PENDING;

        Member member = Member.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .role(finalRole)
                .status(finalStatus)
                .build();

        return memberRepository.save(member).getNo();
    }

    public Member login(String id, String password) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }

    @Transactional
    public void changePassword(String id, String newPassword) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.updatePassword(newPassword);
    }

    @Transactional
    public void approveMember(String id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.approveMember();
    }

    public List<MemberDto> findAllMembers() {
        return memberRepository.findAll().stream()
                .map(Member::toDto)
                .collect(Collectors.toList());
    }
    
    public MemberDto findMemberById(String id) {
        return memberRepository.findById(id)
                .map(Member::toDto)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }
}