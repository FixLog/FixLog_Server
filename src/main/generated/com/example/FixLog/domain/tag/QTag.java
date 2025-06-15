package com.example.FixLog.domain.tag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTag is a Querydsl query type for Tag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTag extends EntityPathBase<Tag> {

    private static final long serialVersionUID = -1474567286L;

    public static final QTag tag = new QTag("tag");

    public final EnumPath<TagCategory> tagCategory = createEnum("tagCategory", TagCategory.class);

    public final NumberPath<Long> tagId = createNumber("tagId", Long.class);

    public final StringPath tagInfo = createString("tagInfo");

    public final StringPath tagName = createString("tagName");

    public QTag(String variable) {
        super(Tag.class, forVariable(variable));
    }

    public QTag(Path<? extends Tag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTag(PathMetadata metadata) {
        super(Tag.class, metadata);
    }

}

