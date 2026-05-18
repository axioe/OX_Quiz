package com.my.ox_quiz.dto;

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
public class QuizDto {
    private Long id;
    private String content;
    private Boolean answer;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}