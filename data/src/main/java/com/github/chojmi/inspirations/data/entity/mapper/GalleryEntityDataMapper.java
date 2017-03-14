package com.github.chojmi.inspirations.data.entity.mapper;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.github.chojmi.inspirations.data.entity.PhotoEntity;
import com.github.chojmi.inspirations.data.entity.gallery.GalleryEntity;
import com.github.chojmi.inspirations.domain.model.Photo;

import java.util.List;

public class GalleryEntityDataMapper {
    private Photo transform(PhotoEntity photoEntity) {
        return Photo.create(photoEntity.getUrl());
    }

    private List<Photo> transform(List<PhotoEntity> photoEntities) {
        return Stream.of(photoEntities).map(this::transform).collect(Collectors.toList());
    }

    public List<Photo> transform(GalleryEntity galleryEntity) {
        return transform(galleryEntity.getPhoto());
    }
}
