package com.example.fixlog.mock;

import com.example.fixlog.domain.bookmark.BookmarkFolder;
import com.example.fixlog.domain.member.Member;
import com.example.fixlog.repository.MemberRepository;
import com.example.fixlog.repository.bookmark.BookmarkFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookmarkFolderTestDataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final BookmarkFolderRepository bookmarkFolderRepository;

    @Override
    public void run(String... args) {
        if (bookmarkFolderRepository.count() == 0) {
            Member member = memberRepository.findByEmail("test1@example.com")
                    .orElseThrow(() -> new IllegalStateException("test1@example.com 사용자가 존재하지 않습니다."));

            BookmarkFolder defaultFolder = new BookmarkFolder("기본 폴더", member);
            BookmarkFolder etcFolder = new BookmarkFolder("그외 폴더", member);

            bookmarkFolderRepository.saveAll(List.of(defaultFolder, etcFolder));

            System.out.println("테스트용 북마크 폴더 2개 생성 완료");
        }
    }
}

