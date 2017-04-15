package com.github.chojmi.inspirations.presentation.mapper.gallery;

import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

public class PhotoDataMapper {

    @Inject
    public PhotoDataMapper() {
    }

    private Photo transform(PhotoEntity photo) {
        return Photo.create(photo.getTitle(), photo.getUrl());
    }

    public List<Photo> transform(List<PhotoEntity> photos) {
        List<Photo> result = new ArrayList<>();
        Observable.fromIterable(photos).map(this::transform).toList().subscribe(result::addAll, Timber::e);
        return result;
    }

    public List<Photo> transform(GalleryEntity galleryEntity) {
        return transform(galleryEntity.getPhoto());
    }
}