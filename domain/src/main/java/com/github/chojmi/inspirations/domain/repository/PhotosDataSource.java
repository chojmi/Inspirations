package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;

import io.reactivex.Flowable;

public interface PhotosDataSource {
    Flowable<PhotoFavsEntity> loadPhotoFavs(String photoId);

    Flowable<PhotoCommentsEntity> loadPhotoComments(String photoId);
}
