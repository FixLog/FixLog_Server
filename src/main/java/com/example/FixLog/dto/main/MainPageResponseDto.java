package com.example.FixLog.dto.main;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MainPageResponseDto {
    private String profileImageUrl;
    private List<MainPagePostResponseDto> posts;
}
