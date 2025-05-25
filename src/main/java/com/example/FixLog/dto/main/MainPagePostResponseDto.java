package com.example.FixLog.dto.main;

import com.example.FixLog.domain.post.PostTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class MainPagePostResponseDto {
    private String postTitle;
    private String coverImage;
    private List<PostTag> tags; // private List<Long> tags;
    private String profileImageUrl;
    private String nickname;
    private LocalDate createdAt; // 여기서는 LocalDateTime까진 필요 없으니까
}
