package com.example.fixlog.mock;

import com.example.fixlog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.fixlog.domain.member.Member;
import com.example.fixlog.domain.member.SocialType;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberTestDataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;

    @Override
    public void run(String... args) {
        if (memberRepository.count() == 0) {
            Member member1 = Member.of("test1@example.com", "1234", "가나다", SocialType.EMAIL);
            Member member2 = Member.of("test2@example.com", "1234", "라마바", SocialType.EMAIL);
            memberRepository.saveAll(List.of(member1, member2));
            System.out.println("테스트용 멤버 2명 삽입 완료");
        }
    }
}
