package com.example.FixLog.domain.post.repository;

import com.example.FixLog.domain.post.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}
