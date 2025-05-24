package com.example.fixlog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 사용자 아이디입니다."),
    USER_EMAIL_NOT_FOUNT(HttpStatus.NOT_FOUND, "회원 이메일을 찾을 수 없습니다."),
    MEMBERID_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 ID를 찾을 수 없습니다"),
    MEMBEREMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 이메일을 찾을 수 없습니다."),
    CANNOT_FOLLOW_SELF(HttpStatus.BAD_REQUEST, "자기 자신은 팔로우할 수 없습니다"),
    CANNOT_UNFOLLOW_SELF(HttpStatus.BAD_REQUEST, "자기 자신은 언팔로우할 수 없습니다"),
    ALREADY_FOLLOWING(HttpStatus.CONFLICT, "이미 팔로우 중입니다"),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로우 관계가 존재하지 않습니다"),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "중복된 이메일입니다"),
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "중복된 닉네임입니다"),
    SELF_FOLLOW_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "자기 자신은 팔로우할 수 없습니다"),
    SELF_UNFOLLOW_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "자기 자신은 언팔로우할 수 없습니다"),
    ALREADY_FOLLOWING(HttpStatus.CONFLICT, "이미 팔로우 중입니다"),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로우 관계가 존재하지 않습니다"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 태그 번호입니다.");

    private final HttpStatus status;
    private final String message;
}