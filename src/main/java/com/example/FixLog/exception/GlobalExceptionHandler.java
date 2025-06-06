package com.example.FixLog.exception;

import com.example.FixLog.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Response<?>> handleDuplicateException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        ex.printStackTrace();
        return new ResponseEntity<>(Response.fail(errorCode.getMessage()), errorCode.getStatus());
    }
}
