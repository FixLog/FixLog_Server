package com.example.FixLog.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TagResponseDto {
    private List<TagDto> tags;
    private int totalPages;
}
