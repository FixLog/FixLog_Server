package com.example.FixLog.domain.mainPage.dto.search;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SearchPostDto {
    private Long postId;
    private String title;
    private String content;
    private String coverImageUrl;
    private String writerNickname;
    private String writerProfileImage;
    private List<String> tags; // 예: [“spring-boot”, “jwt”, “java”]
    private LocalDateTime createdAt;
    private int likeCount;
    private int bookmarkCount;
}
