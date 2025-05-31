package com.example.FixLog.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuplicateCheckResponseDto {
    private boolean duplicated;
}