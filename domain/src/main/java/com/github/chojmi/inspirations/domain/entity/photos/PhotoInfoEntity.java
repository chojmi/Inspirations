package com.github.chojmi.inspirations.domain.entity.photos;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;

public interface PhotoInfoEntity extends PhotoEntity {
    int getCommentsCount();

    boolean isFav();
}
