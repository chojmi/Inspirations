package com.github.chojmi.inspirations.data.source.repository;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeListEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class PhotosRepository implements PhotosDataSource {

    private final PhotosDataSource photosRemoteDataSource;

    private final PhotosDataSource photosLocalDataSource;

    @Inject
    PhotosRepository(@Remote @NonNull PhotosDataSource photosRemoteDataSource,
                     @Local @NonNull PhotosDataSource photosLocalDataSource) {
        this.photosRemoteDataSource = checkNotNull(photosRemoteDataSource);
        this.photosLocalDataSource = checkNotNull(photosLocalDataSource);
    }

    @Override
    public Observable<PhotoInfoEntity> loadPhotoInfo(String photoId) {
        return Observable.concat(photosLocalDataSource.loadPhotoInfo(photoId),
                photosRemoteDataSource.loadPhotoInfo(photoId))
                .filter(photoInfoEntity -> photoInfoEntity != null)
                .firstElement()
                .toObservable();
    }

    @Override
    public Observable<PhotoFavsEntity> loadPhotoFavs(String photoId, int page) {
        return Observable.concat(photosLocalDataSource.loadPhotoFavs(photoId, page),
                photosRemoteDataSource.loadPhotoFavs(photoId, page))
                .filter(photoFavsEntity -> photoFavsEntity != null)
                .firstElement()
                .toObservable();
    }

    @Override
    public Observable<PhotoCommentsEntity> loadPhotoComments(String photoId) {
        return Observable.concat(photosLocalDataSource.loadPhotoComments(photoId),
                photosRemoteDataSource.loadPhotoComments(photoId))
                .filter(photoCommentsEntity -> photoCommentsEntity != null)
                .firstElement()
                .toObservable();
    }

    @Override
    public Observable<PhotoSizeListEntity> loadPhotoSizes(String photoId) {
        return Observable.concat(photosLocalDataSource.loadPhotoSizes(photoId),
                photosRemoteDataSource.loadPhotoSizes(photoId))
                .filter(photoSizeList -> photoSizeList != null)
                .firstElement()
                .toObservable();
    }

    @Override
    public Observable<GalleryEntity> loadSearchPhoto(String text) {
        return Observable.concat(photosLocalDataSource.loadSearchPhoto(text),
                photosRemoteDataSource.loadSearchPhoto(text))
                .filter(gallery -> gallery != null)
                .firstElement()
                .toObservable();
    }

    @Override
    public Observable<Void> addComment(String photoId, String commentText) {
        return photosRemoteDataSource.addComment(photoId, commentText);
    }
}
