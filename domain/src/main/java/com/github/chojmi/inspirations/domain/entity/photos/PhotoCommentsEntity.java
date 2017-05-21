package com.github.chojmi.inspirations.domain.entity.photos;

import java.util.List;

public interface PhotoCommentsEntity<V extends CommentEntity> {
    List<V> getComments();

    String getPhotoId();
}
