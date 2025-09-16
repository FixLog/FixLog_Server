package com.example.FixLog.domain.mainPage.service.impl;

import com.example.FixLog.domain.mainPage.dto.search.SearchPostDto;
import com.example.FixLog.domain.post.repository.PostRepository;
import com.example.FixLog.domain.tag.repository.TagRepository;
import com.example.FixLog.domain.mainPage.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Override
    public Page<SearchPostDto> searchPosts(String keyword, List<String> tags, Pageable pageable) {
        System.out.println("[검색 요청] 키워드: " + keyword + ", 태그: " + tags + ", 페이지 번호: " + pageable.getPageNumber());
        Page<SearchPostDto> result;

        if (tags == null || tags.isEmpty()) {
            result = postRepository.searchByKeywordAndTags(keyword, null, pageable);
        } else {
            result = postRepository.searchByKeywordAndTags(keyword, tags, pageable);
        }

        // 검색 시 태그는 카테고리별로 1개씩만 전달되므로, 우선순위 계산 없이 그대로 전달
        System.out.println("[검색 결과] 총 개수: " + result.getTotalElements());
        return result;
    }
}