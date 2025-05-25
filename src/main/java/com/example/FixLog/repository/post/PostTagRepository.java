package com.example.FixLog.repository.post;

import com.example.FixLog.domain.post.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}
