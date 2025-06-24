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
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(BIG_CATEGORY, "backend"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(BIG_CATEGORY, "machine-learning"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(BIG_CATEGORY, "web"));

        // MAJOR_CATEGORY
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "django"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "spring-boot"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "next.js"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "keras"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "pytorch"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "scikit-learn"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "node.js"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "react"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MAJOR_CATEGORY, "react-native"));

        // MIDDLE_CATEGORY
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "css"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "javascript"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "r"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "json"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "java"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "haskell"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "python"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MIDDLE_CATEGORY, "c"));

        // MINOR_CATEGORY
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "null-pointer-exception", "NullPointerException"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "500-error", "500 Internal Server Error"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "cors-error", "CORS 정책 오류"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "db-timeout", "Database connection timeout"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "class-not-found", "ClassNotFoundException"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "undefined-property", "Cannot read property of undefined"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "state-missing", "상태(state) 업데이트 누락"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "http-error", "HTTP 에러"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "render-loop", "렌더링 무한 루프"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "style-break", "스타일 깨짐"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "404-error", "404 Not Found"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "permission-error", "Permission Error"));
        addIfNotExist(tagsToInsert, existingTagNames, Tag.of(MINOR_CATEGORY, "out-of-memory", "OutOfMemoryError"));


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
