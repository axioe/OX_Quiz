package com.my.ox_quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer answerTrue;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer answerFalse;

    @Builder
    public Member(Long no, String id, String password, RoleType role, MemberStatus status, Integer answerTrue, Integer answerFalse) {
        this.no = no;
        this.id = id;
        this.password = password;
        this.role = role;
        this.status = status;
        this.answerTrue = answerTrue != null ? answerTrue : 0;
        this.answerFalse = answerFalse != null ? answerFalse : 0;
    }

    // 비즈니스 로직: 회원 본인의 비밀번호 변경
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    // 비즈니스 로직: 관리자가 회원의 승인 상태 변경 (PENDING -> APPROVED)
    public void approveMember() {
        this.status = MemberStatus.APPROVED;
    }

    // 비즈니스 로직: 퀴즈 정답 시 맞은 수 누적 (+1)
    public void increaseAnswerTrue() {
        this.answerTrue += 1;
    }

    // 비즈니스 로직: 퀴즈 오답 시 틀린 수 누적 (+1)
    public void increaseAnswerFalse() {
        this.answerFalse += 1;
    }

    // 엔티티를 DTO로 변환하는 규칙 메서드
    public com.my.ox_quiz.dto.MemberDto toDto() {
        return com.my.ox_quiz.dto.MemberDto.builder()
                .no(this.no)
                .id(this.id)
                .password(this.password)
                .role(this.role)
                .status(this.status)
                .answerTrue(this.answerTrue)
                .answerFalse(this.answerFalse)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}