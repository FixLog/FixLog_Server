package com.example.FixLog.service.impl;

import com.example.FixLog.domain.tag.Tag;
import com.example.FixLog.dto.search.SearchPostDto;
import com.example.FixLog.repository.post.PostRepository;
import com.example.FixLog.repository.tag.TagRepository;
import com.example.FixLog.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Override
    public Page<SearchPostDto> searchPosts(String keyword, List<String> tags, Pageable pageable) {
        if (tags == null || tags.isEmpty()) {
            return postRepository.searchByKeywordAndTags(keyword, null, pageable);
        }

        // 검색 시 태그는 카테고리별로 1개씩만 전달되므로, 우선순위 계산 없이 그대로 전달
        return postRepository.searchByKeywordAndTags(keyword, tags, pageable);
    }
}