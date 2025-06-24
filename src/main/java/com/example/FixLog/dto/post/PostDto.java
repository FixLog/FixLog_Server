package com.example.FixLog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostDto {
    private Long userId;
    private String nickname;
    private String writerProfileImage;
    private String postTitle;
    private String coverImageUrl;
    private String problem;
    private String errorMessage;
    private String environment;
    private String reproduceCode;
    private String solutionCode;
    private String causeAnalysis;
    private String referenceLink;
    private String extraContent;
    private List<String> tags;
}
