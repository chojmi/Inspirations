package com.github.chojmi.inspirations.domain.entity.gallery;

import java.util.List;

public interface GalleryEntity<V extends PhotoEntity> {
    List<V> getPhoto();
}
