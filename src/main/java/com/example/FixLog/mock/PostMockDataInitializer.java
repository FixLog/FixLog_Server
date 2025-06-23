package com.example.FixLog.mock;

import com.example.FixLog.domain.bookmark.Bookmark;
import com.example.FixLog.domain.like.PostLike;
import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.post.Post;
import com.example.FixLog.domain.post.PostTag;
import com.example.FixLog.domain.tag.Tag;
import com.example.FixLog.repository.MemberRepository;
import com.example.FixLog.repository.bookmark.BookmarkFolderRepository;
import com.example.FixLog.repository.bookmark.BookmarkRepository;
import com.example.FixLog.repository.like.PostLikeRepository;
import com.example.FixLog.repository.post.PostRepository;
import com.example.FixLog.repository.post.PostTagRepository;
import com.example.FixLog.repository.tag.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Order(6)
@RequiredArgsConstructor
public class PostMockDataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final PostLikeRepository postLikeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkFolderRepository bookmarkFolderRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (postRepository.count() > 8) return; // 기존 1개 외 추가

        Optional<Member> memberOpt = memberRepository.findByEmail("test2@example.com");
        if (memberOpt.isEmpty()) {
            System.out.println("test2@example.com 회원 없음");
            return;
        }

        Member member = memberOpt.get();
        Map<String, Tag> tagMap = tagRepository.findAll().stream()
                .collect(Collectors.toMap(Tag::getTagName, tag -> tag));

        List<String[]> config = List.of(
                new String[]{"backend", "spring-boot", "java", "null-pointer-exception", "500-error"},
                new String[]{"frontend", "react", "javascript", "undefined-property", "state-missing"},
                new String[]{"machine-learning", "keras", "python", "out-of-memory", "http-error"},
                new String[]{"backend", "node.js", "json", "cors-error", "404-error"},
                new String[]{"frontend", "next.js", "css", "style-break", "render-loop"},
                new String[]{"machine-learning", "scikit-learn", "r", "class-not-found", "permission-error"}
        );

        for (int i = 0; i < config.size(); i++) {
            String[] tags = config.get(i);
            Post post = Post.builder()
                    .userId(member)
                    .postTitle("테스트 업그레이드 " + (i + 2))
                    .coverImage("https://cdn.example.com/images/test" + (i + 2) + ".jpg")
                    .problem("이 게시물은 문제 설명이 200자를 넘도록 작성되었습니다. 문제 발생 상황, 재현 과정, 로그, 화면 캡처 등 다양한 정보가 포함될 수 있습니다. 이 텍스트는 말줄임표가 잘 붙는지 확인하기 위한 용도로 작성되었으며, 검색 결과에서는 200자까지만 보여야 합니다. 이후 내용은 생략될 수 있습니다. 추가 텍스트를 더 붙입니다. 더 붙입니다. 더 붙입니다.")
                    .errorMessage("이건 에러다 keyword 포함")
                    .environment("환경 정보")
                    .reproduceCode("재현 코드")
                    .solutionCode("해결 코드")
                    .causeAnalysis("원인 분석")
                    .referenceLink("https://example.com")
                    .extraContent("추가 설명")
                    .createdAt(LocalDateTime.now())
                    .editedAt(LocalDateTime.now())
                    .build();
            postRepository.save(post);

            List<PostTag> postTags = Arrays.stream(tags)
                    .map(tagMap::get)
                    .filter(Objects::nonNull)
                    .map(tag -> new PostTag(post, tag))
                    .toList();
            postTagRepository.saveAll(postTags);

            postLikeRepository.save(new PostLike(member, post));

            bookmarkFolderRepository.findFirstByUserId(member).ifPresent(folder -> {
                Bookmark bookmark = new Bookmark(folder, post);
                bookmarkRepository.save(bookmark);
            });
        }

        System.out.println("test2@example.com 사용자의 게시물 6개 + 태그/좋아요/북마크 생성 완료");
    }
}
