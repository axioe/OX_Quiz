package com.my.ox_quiz.repository;

import com.my.ox_quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // 데이터베이스에서 무작위로 1개의 퀴즈를 추출하는 쿼리 (MySQL/H2 호환)
    @Query(value = "SELECT * FROM quiz ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Quiz> findRandomQuiz();
}