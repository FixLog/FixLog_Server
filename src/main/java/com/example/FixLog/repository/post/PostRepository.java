package com.example.FixLog.repository.post;

import com.example.FixLog.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop12ByOrderByCreatedAtDesc();
    List<Post> findTop12ByOrderByPostLikesDesc();
}
