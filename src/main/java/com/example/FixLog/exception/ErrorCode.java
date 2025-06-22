package com.example.FixLog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NICKNAME_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 사용자 아이디입니다."),
    USER_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 이메일을 찾을 수 없습니다."),
    USER_DELETED(HttpStatus.FORBIDDEN, "탈퇴한 회원입니다."),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "중복된 이메일입니다"),
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "중복된 닉네임입니다"),
    ALREADY_FOLLOWING(HttpStatus.CONFLICT, "이미 팔로우 중입니다"),
    SELF_FOLLOW_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "자기 자신은 팔로우할 수 없습니다"),
    SELF_UNFOLLOW_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "자기 자신은 언팔로우할 수 없습니다"),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로우 관계가 존재하지 않습니다"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    SELF_BOOKMARK_NOT_ALLOWED(HttpStatus.NOT_FOUND, "본인 글은 저장할 수 없습니다."),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "북마크를 찾을 수 없습니다."),
    FOLDER_NOT_FOUND(HttpStatus.NOT_FOUND, "폴더를 찾을 수 없습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 태그 번호입니다."),
    SORT_NOT_EXIST(HttpStatus.BAD_REQUEST, "사용할 수 없는 정렬입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    REQUIRED_TAGS_MISSING(HttpStatus.BAD_REQUEST, "태그를 선택해주세요."),
    REQUIRED_CONTENT_MISSING(HttpStatus.BAD_REQUEST, "필수 본문이 입력되지 않았습니다."),
    SAME_AS_OLD_PASSWORD(HttpStatus.BAD_REQUEST, "다른 비밀번호 입력 바랍니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "요청 데이터가 유효하지 않습니다."),
    S3_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "S3 파일 업로드에 실패했습니다."),
    IMAGE_UPLOAD_FAILED(HttpStatus.NOT_FOUND, "이미지 파일이 업로드되지 않았습니다.");

    private final HttpStatus status;
    private final String message;
}
