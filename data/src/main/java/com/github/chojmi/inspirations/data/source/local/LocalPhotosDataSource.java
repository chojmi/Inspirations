package com.github.chojmi.inspirations.data.source.local;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;

public final class LocalPhotosDataSource implements PhotosDataSource {

    @Inject
    public LocalPhotosDataSource() {
    }

    @Override
    public Observable<PhotoFavsEntity> loadPhotoFavs(String photoId) {
        return Observable.empty();
    }

    @Override
    public Observable<PhotoCommentsEntity> loadPhotoComments(String photoId) {
        return Observable.empty();
    }
}
