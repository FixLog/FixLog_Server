package com.example.fixlog.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostRequestDto {
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
