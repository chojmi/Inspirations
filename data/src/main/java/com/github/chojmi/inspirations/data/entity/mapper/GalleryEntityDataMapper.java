package com.github.chojmi.inspirations.data.entity.mapper;

import com.github.chojmi.inspirations.data.entity.PhotoEntity;
import com.github.chojmi.inspirations.data.entity.gallery.GalleryEntity;
import com.github.chojmi.inspirations.domain.model.Photo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import timber.log.Timber;

@Singleton
public class GalleryEntityDataMapper {

    @Inject
    public GalleryEntityDataMapper() {
    }

    private Photo transform(PhotoEntity photoEntity) {
        return Photo.create(photoEntity.getUrl());
    }

    private List<Photo> transform(List<PhotoEntity> photoEntities) {
        List<Photo> result = new ArrayList<>();
        Observable.fromIterable(photoEntities).map(this::transform).toList().subscribe(result::addAll, Timber::e);
        return result;
    }

    public List<Photo> transform(GalleryEntity galleryEntity) {
        return transform(galleryEntity.getPhoto());
    }
}
