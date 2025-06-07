package com.example.FixLog.dto.member.edit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditPasswordRequestDto {
    private String currentPassword;
    private String newPassword;
}
