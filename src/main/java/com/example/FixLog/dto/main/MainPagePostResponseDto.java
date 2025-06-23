package com.example.FixLog.dto.main;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class MainPagePostResponseDto {
    private Long postId;
    private String postTitle;
    private String coverImage;
    private List<String> tags;
    private String writerProfileImageUrl;
    private String nickname;
    private LocalDate createdAt; // 여기서는 LocalDateTime 까진 필요 없으니까
    private int likeCount;
}
