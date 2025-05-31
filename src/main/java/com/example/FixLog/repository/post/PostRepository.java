package com.example.FixLog.repository.post;

import com.example.FixLog.domain.member.Member;
import com.example.FixLog.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop12ByOrderByCreatedAtDesc();
    List<Post> findTop12ByOrderByPostLikesDesc();
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Post> findAllByOrderByPostLikesDesc(Pageable pageable);

    @EntityGraph(attributePaths = {"postLikes"})
    Page<Post> findByUserId(Member userId, Pageable pageable); // 좋아요수 때문
}
