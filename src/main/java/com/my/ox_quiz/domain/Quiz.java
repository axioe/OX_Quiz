package com.my.ox_quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quiz")
public class Quiz extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Boolean answer; // true = O, false = X

    @Column(nullable = false)
    private String writer;

    @Builder
    public Quiz(Long id, String content, Boolean answer, String writer) {
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.writer = writer;
    }

    // 비즈니스 로직: 퀴즈 내용 및 정답 수정
    public void updateQuiz(String content, Boolean answer, String writer) {
        this.content = content;
        this.answer = answer;
        this.writer = writer;
    }

    // 엔티티를 DTO로 변환하는 규칙 메서드
    public com.my.ox_quiz.dto.QuizDto toDto() {
        return com.my.ox_quiz.dto.QuizDto.builder()
                .id(this.id)
                .content(this.content)
                .answer(this.answer)
                .writer(this.writer)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}