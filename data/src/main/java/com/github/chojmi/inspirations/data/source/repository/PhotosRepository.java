package com.github.chojmi.inspirations.data.source.repository;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

public final class PhotosRepository implements PhotosDataSource {

    private final PhotosDataSource photosRemoteDataSource;

    private final PhotosDataSource photosLocalDataSource;

    @Inject
    PhotosRepository(@Remote @NonNull PhotosDataSource photosRemoteDataSource,
                     @Local @NonNull PhotosDataSource photosLocalDataSource) {
        this.photosRemoteDataSource = checkNotNull(photosRemoteDataSource);
        this.photosLocalDataSource = checkNotNull(photosLocalDataSource);
    }

    @Override
    public Observable<List<PersonEntity>> loadPhotoFavs(String photoId) {
        return Observable.concat(photosLocalDataSource.loadPhotoFavs(photoId),
                photosRemoteDataSource.loadPhotoFavs(photoId)).filter(favs -> favs.size() > 0).firstElement().toObservable();
    }

    @Override
    public Observable<List<CommentEntity>> loadPhotoComments(String photoId) {
        return Observable.concat(photosLocalDataSource.loadPhotoComments(photoId),
                photosRemoteDataSource.loadPhotoComments(photoId)).filter(comments -> comments.size() > 0).firstElement().toObservable();
    }
}
