package com.example.FixLog.mock;

import com.example.FixLog.domain.tag.Tag;
import com.example.FixLog.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.FixLog.domain.tag.TagCategory.*;

@Component
@RequiredArgsConstructor
@Order(3)
public class TagTestDataInitializer implements CommandLineRunner {

    private final TagRepository tagRepository;

    @Override
    public void run(String... args) {
        if (tagRepository.count() == 0) {
            Tag tag1 = Tag.of(BIG_CATEGORY, "backend", "백엔드 설명");
            Tag tag2 = Tag.of(MAJOR_CATEGORY, "springboot", "스프링부트 설명");
            Tag tag3 = Tag.of(MAJOR_CATEGORY, "django", "장고 설명");
            Tag tag4 = Tag.of(MIDDLE_CATEGORY, "java", "자바 설명");
            Tag tag5 = Tag.of(MINOR_CATEGORY, "404 not found", "404 에러 설명");
            tagRepository.saveAll(List.of(tag1, tag2, tag3, tag4, tag5));
            System.out.println("임시 태그 4개 삽입 완료");
        }
    }
}
