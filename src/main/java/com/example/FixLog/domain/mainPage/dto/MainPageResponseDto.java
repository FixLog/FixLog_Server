package com.example.FixLog.domain.mainPage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MainPageResponseDto {
    private String userProfileImageUrl;

    private List<MainPagePostResponseDto> posts;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT) // totalPage = 0 인 경우에는 출력되지 않도록
    private int totalPages;

    public MainPageResponseDto(String userProfileImageUrl, List<MainPagePostResponseDto> posts){
        this.userProfileImageUrl = userProfileImageUrl;
        this.posts = posts;
    }
}
