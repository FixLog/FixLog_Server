package com.example.fixlog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "중복된 닉네임입니다"),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "중복된 이메일입니다");

    private final HttpStatus status;
    private final String message;
}