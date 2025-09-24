package com.example.FixLog.domain.post.repository;

import com.example.FixLog.domain.post.domain.PostLike;
import com.example.FixLog.domain.member.domain.Member;
import com.example.FixLog.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserIdAndPostId(Member userId, Post postId);
    Page<PostLike> findByUserId(Member user, Pageable pageable);
}
