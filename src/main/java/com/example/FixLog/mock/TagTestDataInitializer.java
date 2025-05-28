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
            Tag tag1 = Tag.of(MAJOR_CATEGORY, "101");
            Tag tag2 = Tag.of(MAJOR_CATEGORY, "101");
            Tag tag3 = Tag.of(MIDDLE_CATEGORY, "201");
            Tag tag4 = Tag.of(MINOR_CATEGORY, "301");
            tagRepository.saveAll(List.of(tag1, tag2, tag3, tag4));
            System.out.println("임시 태그 4개 삽입 완료");
        }
    }
}
