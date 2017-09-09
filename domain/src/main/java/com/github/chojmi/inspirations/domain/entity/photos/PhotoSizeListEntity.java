package com.github.chojmi.inspirations.domain.entity.photos;

import java.util.List;

public interface PhotoSizeListEntity<V extends PhotoSizeEntity> {
    List<V> getSizes();

    float getRatio();
}
