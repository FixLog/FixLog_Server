package com.example.FixLog.dto.post;

import lombok.Getter;

import java.util.List;

@Getter
public class PostRequestDto {
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
