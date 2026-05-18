package com.my.ox_quiz.dto;

import com.my.ox_quiz.domain.MemberStatus;
import com.my.ox_quiz.domain.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long no;
    private String id;
    private String password;
    private RoleType role;
    private MemberStatus status;
    private Integer answerTrue;
    private Integer answerFalse;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}