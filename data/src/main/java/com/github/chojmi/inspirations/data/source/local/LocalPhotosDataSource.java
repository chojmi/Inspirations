package com.github.chojmi.inspirations.data.source.local;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public final class LocalPhotosDataSource implements PhotosDataSource {

    @Inject
    public LocalPhotosDataSource() {
    }

    @Override
    public Observable<List<PersonEntity>> loadPhotoFavs(String photoId) {
        return Observable.empty();
    }

    @Override
    public Observable<List<CommentEntity>> loadPhotoComments(String photoId) {
        return Observable.empty();
    }
}
