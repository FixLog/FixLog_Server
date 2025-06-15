package com.example.FixLog.domain.like;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostLike is a Querydsl query type for PostLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostLike extends EntityPathBase<PostLike> {

    private static final long serialVersionUID = -1255529902L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostLike postLike = new QPostLike("postLike");

    public final BooleanPath isLiked = createBoolean("isLiked");

    public final NumberPath<Long> likeId = createNumber("likeId", Long.class);

    public final com.example.FixLog.domain.post.QPost postId;

    public final com.example.FixLog.domain.member.QMember userId;

    public QPostLike(String variable) {
        this(PostLike.class, forVariable(variable), INITS);
    }

    public QPostLike(Path<? extends PostLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostLike(PathMetadata metadata, PathInits inits) {
        this(PostLike.class, metadata, inits);
    }

    public QPostLike(Class<? extends PostLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postId = inits.isInitialized("postId") ? new com.example.FixLog.domain.post.QPost(forProperty("postId"), inits.get("postId")) : null;
        this.userId = inits.isInitialized("userId") ? new com.example.FixLog.domain.member.QMember(forProperty("userId"), inits.get("userId")) : null;
    }

}

