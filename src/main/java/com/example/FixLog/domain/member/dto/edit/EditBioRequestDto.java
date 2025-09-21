package com.example.FixLog.domain.member.dto.edit;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EditBioRequestDto {

    @Size(max = 200, message = "소개글은 최대 200자까지 입력 가능합니다.")
    private String bio;
}
