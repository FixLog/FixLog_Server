package com.example.FixLog.mock;

import com.example.FixLog.domain.tag.Tag;
import com.example.FixLog.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.FixLog.domain.tag.TagCategory.*;

@Component
@RequiredArgsConstructor
@Order(4)
public class TagMockDataInitializer implements CommandLineRunner {

    private final TagRepository tagRepository;

    @Override
    public void run(String... args) {
        // 현재 저장된 태그 이름들 (중복 방지용)
        Set<String> existingTagNames = tagRepository.findAll()
                .stream()
                .map(Tag::getTagName)
                .collect(Collectors.toSet());

        List<Tag> tagsToInsert = new ArrayList<>();

        // BIG_CATEGORY
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(BIG_CATEGORY, "백엔드", "backend"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(BIG_CATEGORY, "머신러닝", "machine learning"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(BIG_CATEGORY, "프론트엔드", "frontend"));

        // MAJOR_CATEGORY
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "장고", "django"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "스프링부트", "spring-boot"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "넥스트", "next.js"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "케라스", "keras"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "파이토치", "pytorch"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "사이킷런", "scikit-learn"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "노드", "node.js"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "리액트", "react"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "리액트 네이티브", "react-native"));

        // MIDDLE_CATEGORY
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "CSS", "css"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "자바스크립트", "javascript"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "R", "r"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "JSON", "json"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "자바", "java"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "Haskell", "haskell"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "파이썬", "python"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "C", "c"));

        // MINOR_CATEGORY
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "NullPointerException", "null-pointer-exception"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "500 Internal Server Error", "500-error"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "CORS 정책 오류", "cors-error"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "Database connection timeout", "db-timeout"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "ClassNotFoundException", "class-not-found"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "Cannot read property of undefined", "undefined-property"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "상태(state) 업데이트 누락", "state-missing"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "HTTP 에러", "http-error"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "렌더링 무한 루프", "render-loop"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "스타일 깨짐", "style-break"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "404 Not Found", "404-error"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "Permission Error", "permission-error"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "OutOfMemoryError", "out-of-memory"));

        if (!tagsToInsert.isEmpty()) {
            tagRepository.saveAll(tagsToInsert);
            System.out.println("중복 없는 목업 태그 " + tagsToInsert.size() + "개 삽입 완료");
        } else {
            System.out.println("삽입할 신규 태그가 없습니다.");
        }
    }

    private void addIfNotExist(List<Tag> list, Set<String> existingNames, Tag tag) {
        if (!existingNames.contains(tag.getTagName())) {
            list.add(tag);
        }
    }
}
