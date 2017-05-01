package com.github.chojmi.inspirations.data.source.remote;

import android.support.annotation.VisibleForTesting;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class FakeRemotePhotosDataSource implements PhotosDataSource {
    private List<PersonEntity> fakeFavList = Collections.emptyList();
    private List<CommentEntity> fakeCommentsList = Collections.emptyList();

    @VisibleForTesting
    public void setFakePhotoLists(List<PersonEntity> fakeFavList) {
        this.fakeFavList = fakeFavList;
    }

    @Override
    public Observable<List<PersonEntity>> loadPhotoFavs(String photoId) {
        return Observable.just(fakeFavList);
    }

    @Override
    public Observable<List<CommentEntity>> loadPhotoComments(String photoId) {
        return Observable.just(fakeCommentsList);
    }
}
