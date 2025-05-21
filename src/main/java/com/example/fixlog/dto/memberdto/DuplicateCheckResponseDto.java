package com.example.fixlog.dto.memberdto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuplicateCheckResponseDto {
    private boolean duplicated;
}