package com.github.chojmi.inspirations.data.source.local;

import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeListEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LocalPhotosDataSource implements PhotosDataSource {

    @Inject
    public LocalPhotosDataSource() {
    }

    @Override
    public Observable<PhotoInfoEntity> loadPhotoInfo(String photoId) {
        return Observable.empty();
    }

    @Override
    public Observable<PhotoFavsEntity> loadPhotoFavs(String photoId, int page) {
        return Observable.empty();
    }

    @Override
    public Observable<PhotoCommentsEntity> loadPhotoComments(String photoId) {
        return Observable.empty();
    }

    @Override
    public Observable<PhotoSizeListEntity> loadPhotoSizes(String photoId) {
        return Observable.empty();
    }

    @Override
    public Observable<GalleryEntity> loadSearchPhoto(String text) {
        return Observable.empty();
    }
}
