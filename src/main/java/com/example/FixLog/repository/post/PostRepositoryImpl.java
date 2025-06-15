package com.example.FixLog.repository.post;

import com.example.FixLog.domain.post.Post;
import com.example.FixLog.dto.search.SearchPostDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.FixLog.domain.post.QPost.post;
import static com.example.FixLog.domain.post.QPostTag.postTag;
import static com.example.FixLog.domain.tag.QTag.tag;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchPostDto> searchByKeywordAndTags(String keyword, List<String> tags, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // 1. 키워드 조건
        //containsIgnoreCase 쓰려면 post에서 String problem, String errorMessage의 @Lob 어노테이션을 없애야 함
        // + keyword 검색 시 @Lob 필드(CLOB)에는 lower() 사용 시 에러 발생 가능
        // 따라서 단순 like 연산만 사용하여 처리함
        if (keyword != null && !keyword.isBlank()) {
            builder.and(post.problem.like("%" + keyword + "%")
                    .or(post.errorMessage.like("%" + keyword + "%"))
                    .or(post.postTitle.like("%" + keyword + "%")));
        }

        // 2. 태그 조건 (AND 조건)
        if (tags != null && !tags.isEmpty()) {
            NumberExpression<Long> count = postTag.tagId.tagId.count();

            JPQLQuery<Long> subQuery = queryFactory
                    .select(postTag.postId.postId)
                    .from(postTag)
                    .where(postTag.tagId.tagName.in(tags))
                    .groupBy(postTag.postId.postId)
                    .having(count.eq((long) tags.size()));

            builder.and(post.postId.in(subQuery));
        }

        // 3. 본문 조회
        List<Post> result = queryFactory
                .selectFrom(post)
                .leftJoin(post.postTags, postTag).fetchJoin()
                .leftJoin(postTag.tagId, tag).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 4. DTO 매핑
        List<SearchPostDto> content = result.stream().map(p -> SearchPostDto.builder()
                .postId(p.getPostId())
                .title(p.getPostTitle())
                .content(p.getProblem().length() > 200 ? p.getProblem().substring(0, 200) + "…" : p.getProblem())
                .writerNickname(p.getUserId().getNickname())
                .writerProfileImage(p.getUserId().getProfileImageUrl())
                .tags(p.getPostTags().stream().map(pt -> pt.getTagId().getTagName()).toList())
                .createdAt(p.getCreatedAt())
                .likeCount(p.getPostLikes().size())
                .bookmarkCount(p.getBookmarks().size())
                .build()
        ).collect(Collectors.toList());

        // 5. 페이징 처리
        return PageableExecutionUtils.getPage(content, pageable,
                () -> queryFactory
                        .select(post.postId)
                        .from(post)
                        .where(builder)
                        .fetch()
                        .size());
    }
}