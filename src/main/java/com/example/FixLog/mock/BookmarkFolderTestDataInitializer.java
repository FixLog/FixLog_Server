package com.example.FixLog.mock;

import com.example.FixLog.domain.bookmark.domain.BookmarkFolder;
import com.example.FixLog.domain.member.repository.MemberRepository;
import com.example.FixLog.domain.bookmark.repository.BookmarkFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
public class BookmarkFolderTestDataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final BookmarkFolderRepository bookmarkFolderRepository;

    @Override
    public void run(String... args) {
        if (bookmarkFolderRepository.count() == 0) {
            memberRepository.findByEmail("test1@example.com").ifPresentOrElse(member -> {
                BookmarkFolder defaultFolder = new BookmarkFolder(member, "default folder");
                BookmarkFolder etcFolder = new BookmarkFolder(member, "그외 폴더");
                bookmarkFolderRepository.saveAll(List.of(defaultFolder, etcFolder));
                System.out.println("테스트용 북마크 폴더 2개 생성 완료");
            }, () -> {
                System.out.println("test1@example.com 사용자가 존재하지 않아 폴더 생성 생략됨");
            });
        }
    }
}

