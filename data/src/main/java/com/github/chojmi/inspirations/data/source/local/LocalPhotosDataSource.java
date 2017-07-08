package com.github.chojmi.inspirations.data.source.local;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class LocalPhotosDataSource implements PhotosDataSource {

    @Inject
    public LocalPhotosDataSource() {
    }

    @Override
    public Flowable<PhotoFavsEntity> loadPhotoFavs(String photoId) {
        return Flowable.empty();
    }

    @Override
    public Flowable<PhotoCommentsEntity> loadPhotoComments(String photoId) {
        return Flowable.empty();
    }
}
