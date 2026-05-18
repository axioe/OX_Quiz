package com.my.ox_quiz.repository;

import com.my.ox_quiz.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(String id);

    long countByRole(com.my.ox_quiz.domain.RoleType role);
}