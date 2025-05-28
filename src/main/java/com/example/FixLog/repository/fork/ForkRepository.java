package com.example.FixLog.repository.fork;

import com.example.FixLog.domain.fork.Fork;
import com.example.FixLog.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ForkRepository extends JpaRepository<Fork, Long> {
    @Query("""
    SELECT f.originalPostId.postId, COUNT(f)
    FROM Fork f
    WHERE f.originalPostId IN :posts
    GROUP BY f.originalPostId.postId
    """)
    List<Object[]> countForksByOriginalPosts(@Param("posts") List<Post> posts); // 원본글 기반 포크 수
}
