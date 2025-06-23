package com.example.FixLog.repository.tag;

import com.example.FixLog.domain.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findAll(Pageable pageable);
    Optional<Tag> findByTagName(String tagName);
}
