package com.my.ox_quiz.service;

import com.my.ox_quiz.domain.Member;
import com.my.ox_quiz.domain.Quiz;
import com.my.ox_quiz.dto.QuizDto;
import com.my.ox_quiz.repository.MemberRepository;
import com.my.ox_quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

    private final QuizRepository quizRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public Long createQuiz(QuizDto dto) {
        if (dto.getContent() == null || dto.getContent().trim().isEmpty() ||
                dto.getWriter() == null || dto.getWriter().trim().isEmpty()) {
            throw new IllegalArgumentException("퀴즈 내용과 작성자는 필수 입력 항목입니다.");
        }

        Quiz quiz = Quiz.builder()
                .content(dto.getContent())
                .answer(dto.getAnswer())
                .writer(dto.getWriter())
                .build();

        return quizRepository.save(quiz).getId();
    }


    public List<QuizDto> findAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(Quiz::toDto)
                .collect(Collectors.toList());
    }


    public QuizDto findQuizById(Long id) {
        return quizRepository.findById(id)
                .map(Quiz::toDto)
                .orElseThrow(() -> new IllegalArgumentException("해당 퀴즈가 존재하지 않습니다."));
    }


    @Transactional
    public void updateQuiz(QuizDto dto) {
        Quiz quiz = quizRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 퀴즈가 존재하지 않습니다."));

        if (dto.getContent() == null || dto.getContent().trim().isEmpty() ||
                dto.getWriter() == null || dto.getWriter().trim().isEmpty()) {
            throw new IllegalArgumentException("퀴즈 내용과 작성자는 필수 입력 항목입니다.");
        }

        quiz.updateQuiz(dto.getContent(), dto.getAnswer(), dto.getWriter());
    }

    @Transactional
    public void deleteQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 퀴즈가 존재하지 않습니다."));
        quizRepository.delete(quiz);
    }

    public QuizDto getRandomQuiz() {
        return quizRepository.findRandomQuiz()
                .map(Quiz::toDto)
                .orElseThrow(() -> new IllegalStateException("등록된 문제가 없습니다."));
    }

    @Transactional
    public boolean checkAnswerAndSaveScore(Long quizId, Boolean submittedAnswer, String memberId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("퀴즈 정보를 확인할 수 없습니다."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 확인할 수 없습니다."));


        boolean isCorrect = quiz.getAnswer().equals(submittedAnswer);

        if (isCorrect) {
            member.increaseAnswerTrue();
        } else {
            member.increaseAnswerFalse();
        }

        return isCorrect;
    }
}