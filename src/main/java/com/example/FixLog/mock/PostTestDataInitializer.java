package com.example.FixLog.mock;

import com.example.FixLog.domain.bookmark.Bookmark;
import com.example.FixLog.domain.like.PostLike;
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
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Order(4) // Member, Tag, BookmarkFolder 이후에 실행
@RequiredArgsConstructor
public class PostTestDataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final PostLikeRepository postLikeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkFolderRepository bookmarkFolderRepository;

    @Override
    public void run(String... args) {
        if (postRepository.count() == 0) {
            memberRepository.findByEmail("test1@example.com").ifPresentOrElse(member -> {

                // 1. 게시글 생성
                Post post = Post.builder()
                        .userId(member)
                        .postTitle("개발을 하다 보면 많은 에러를 만난다")
                        .coverImage("https://cdn.example.com/images/test1.jpg")
                        .problem("만나고 싶지 않다")
                        .errorMessage("에러메세지 ~~ ")
                        .environment("스프링부트")
                        .reproduceCode("여긴 뭘까요")
                        .solutionCode("해결 !!")
                        .causeAnalysis("이유를 모름")
                        .referenceLink("no_error@@.com")
                        .extraContent("추가 설명입니다.")
                        .createdAt(LocalDateTime.now())
                        .editedAt(LocalDateTime.now())
                        .build();
                postRepository.save(post);

                // 2. 태그 연결
                List<Tag> tags = tagRepository.findAll();
                if (!tags.isEmpty()) {
                    List<PostTag> postTags = tags.subList(0, Math.min(2, tags.size())).stream()
                            .map(tag -> new PostTag(post, tag))
                            .toList();
                    postTagRepository.saveAll(postTags);
                }

                // 3. 좋아요 추가
                PostLike postLike = new PostLike(member, post);
                postLikeRepository.save(postLike);

                // 4. 북마크 추가 (기본 폴더 사용)
                bookmarkFolderRepository.findFirstByUserId(member).ifPresent(folder -> {
                    Bookmark bookmark = new Bookmark(folder, post);
                    bookmarkRepository.save(bookmark);
                });

                System.out.println("테스트용 게시글 1개, 태그/좋아요/북마크까지 생성 완료");

            }, () -> {
                System.out.println("test1@example.com 사용자가 없어 게시글 생성 생략됨");
            });
        }
    }
}
