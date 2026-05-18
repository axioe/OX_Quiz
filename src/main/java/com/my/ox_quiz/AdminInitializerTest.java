package com.my.ox_quiz;

import com.my.ox_quiz.domain.Member;
import com.my.ox_quiz.domain.MemberStatus;
import com.my.ox_quiz.domain.RoleType;
import com.my.ox_quiz.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class AdminInitializerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void insertAdminAccount() {

        Optional<Member> existingAdmin = memberRepository.findById("root");

        if (existingAdmin.isEmpty()) {
            String encryptedPassword = "admin";

            Member admin = Member.builder()
                    .id("root")
                    .password(encryptedPassword)
                    .role(RoleType.ADMIN)
                    .status(MemberStatus.APPROVED)
                    .answerTrue(0)
                    .answerFalse(0)
                    .build();

            memberRepository.save(admin);
            System.out.println("====== [초기화 완료] 관리자 계정(root)이 DB에 성공적으로 등록되었습니다. ======");
        } else {
            System.out.println("====== [안내] 이미 관리자 계정(root)이 존재하므로 생략합니다. ======");
        }
    }
}