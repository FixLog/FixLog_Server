package com.example.FixLog.domain.mainPage.dto.search;

import lombok.Getter;

import java.util.List;

/**
 * 게시글 검색 요청 DTO
 * - keyword와 태그 카테고리 정보를 담아야 됨
 */
@Getter
public class SearchRequestDto {

    private String keyword;               // 텍스트 검색어

    private String bigCategory;          // 택1 (ex: 백엔드)
    private String majorCategory;        // 택1 (ex: Spring)
    private String middleCategory;       // 택1 (ex: JPA)
    private List<String> minorCategories; // 복수 선택 가능 (ex: JWT, CI/CD 등)

    private Integer page;                // 1부터 시작
    private String sort;                 // 정렬 기준 (ex: recent, popular)
}