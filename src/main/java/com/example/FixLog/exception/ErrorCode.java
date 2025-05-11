package com.example.FixLog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ID_DUPLICATED(HttpStatus.CONFLICT, "중복된 아이디입니다"),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "중복된 이메일입니다");
    // 위 작성해둔 코드는 예시이니 필요시 수정 후 사용해주세요. 그때 이 주석도 지워주세요!
    // 이어서 필요한 에러 코드 작성 후 사용해주시면 됩니다.

    private final HttpStatus status;
    private final String message;
}
