package com.example.fixlog.repository.like;

import com.example.fixlog.domain.like.PostLike;
import com.example.fixlog.domain.member.Member;
import com.example.fixlog.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
