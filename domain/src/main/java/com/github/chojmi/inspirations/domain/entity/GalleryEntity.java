package com.github.chojmi.inspirations.domain.entity;

import java.util.List;

public interface GalleryEntity<V extends PhotoEntity> {
    List<V> getPhoto();
}
