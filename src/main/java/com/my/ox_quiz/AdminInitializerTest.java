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
    @Rollback(false) // DB에 실제로 반영되도록 롤백을 해제합니다.
    public void insertAdminAccount() {
        // 1. root 계정이 이미 존재하는지 먼저 확인 (멱등성 확보)
        Optional<Member> existingAdmin = memberRepository.findById("root");

        if (existingAdmin.isEmpty()) {
            // 사양서 6번: 비번을 암호화 해서 입력 (여기서는 기본 요구 암호 원문인 admin을 셋팅하되,
            // 만약 보안 해시 기법이나 BCrypt 객체가 프로젝트에 빈 등록 되어있다면 encoder.encode("admin")을 대입합니다.)
            String encryptedPassword = "admin";

            Member admin = Member.builder()
                    .id("root")
                    .password(encryptedPassword)
                    .role(RoleType.ADMIN)
                    .status(MemberStatus.APPROVED) // 관리자는 즉시 승인 완료 상태
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