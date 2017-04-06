package com.github.chojmi.inspirations.domain.entity.gallery;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;

import java.util.List;

public interface GalleryEntity<V extends PhotoEntity> {
    List<V> getPhoto();
}
