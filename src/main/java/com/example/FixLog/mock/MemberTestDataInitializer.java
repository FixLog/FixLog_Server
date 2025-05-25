package com.example.FixLog.mock;

import com.example.FixLog.domain.bookmark.BookmarkFolder;
import com.example.FixLog.repository.MemberRepository;
import com.example.FixLog.repository.bookmark.BookmarkFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.member.SocialType;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberTestDataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final BookmarkFolderRepository bookmarkFolderRepository;

    @Override
    public void run(String... args) {
        if (memberRepository.count() == 0) {
            Member member1 = Member.of("test1@example.com", "1234", "가나다", SocialType.EMAIL);
            Member member2 = Member.of("test2@example.com", "1234", "라마바", SocialType.EMAIL);
            memberRepository.saveAll(List.of(member1, member2));

            BookmarkFolder folder1 = new BookmarkFolder(member1);
            BookmarkFolder folder2 = new BookmarkFolder(member2);
            bookmarkFolderRepository.saveAll(List.of(folder1, folder2));

            System.out.println("테스트용 멤버 2명 삽입 완료");
        }
    }
}
