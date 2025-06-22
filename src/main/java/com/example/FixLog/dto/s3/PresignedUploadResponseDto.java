package com.example.FixLog.dto.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PresignedUploadResponseDto {
    private String presignedUrl;  // PUT 요청용 presigned URL
    private String fileUrl;       // 업로드 완료 후 접근 가능한 S3 URL
}

