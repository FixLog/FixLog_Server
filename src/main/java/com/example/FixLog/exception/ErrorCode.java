package com.example.fixlog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ID_DUPLICATED(HttpStatus.CONFLICT, "중복된 아이디입니다"),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "중복된 이메일입니다"),
    USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 사용자 아이디입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    POST_LIKE_ALREADY(HttpStatus.CONFLICT, "이미 좋아요한 게시글입니다."),
    POST_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 좋아요입니다.");
    // 위 작성해둔 코드는 예시이니 필요시 수정 후 사용해주세요. 그때 이 주석도 지워주세요!
    // 이어서 필요한 에러 코드 작성 후 사용해주시면 됩니다.

    private final HttpStatus status;
    private final String message;
}
