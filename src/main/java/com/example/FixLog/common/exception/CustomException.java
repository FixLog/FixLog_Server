package com.example.FixLog.common.exception;

import com.example.FixLog.common.response.ErrorStatus;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorStatus errorCode;

    public CustomException(ErrorStatus errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorStatus errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }
}
