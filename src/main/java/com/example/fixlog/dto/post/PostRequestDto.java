package com.example.fixlog.dto.post;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private Long userId; // Fixme : 회원가입 구현 후 삭제 예정
    private String postTitle;
    private String problem;
    private String errorMessage;
    private String environment;
    private String reproduceCode;
    private String solutionCode;
    private String causeAnalysis;
    private String referenceLink;
    private String extraContent;

//    private List<String> postImageUrl;
//    private List<String> tags;
}
