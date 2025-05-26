package com.example.fixlog.repository.post;

import com.example.fixlog.domain.post.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}
