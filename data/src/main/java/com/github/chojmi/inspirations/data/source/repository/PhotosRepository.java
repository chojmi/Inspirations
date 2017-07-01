package com.github.chojmi.inspirations.data.source.repository;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
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
    public Observable<PhotoFavsEntity> loadPhotoFavs(String photoId) {
        return Observable.concat(photosLocalDataSource.loadPhotoFavs(photoId),
                photosRemoteDataSource.loadPhotoFavs(photoId))
                .filter(photoFavsEntity -> photoFavsEntity.getPeople().size() > 0)
                .firstElement()
                .toObservable();
    }

    @Override
    public Observable<PhotoCommentsEntity> loadPhotoComments(String photoId) {
        return Observable.concat(photosLocalDataSource.loadPhotoComments(photoId),
                photosRemoteDataSource.loadPhotoComments(photoId))
                .filter(photoCommentsEntity -> photoCommentsEntity.getComments().size() > 0)
                .firstElement()
                .toObservable();
    }
}
