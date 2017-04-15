package com.github.chojmi.inspirations.data.source.remote;

import android.support.annotation.VisibleForTesting;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class FakeRemotePeopleDataSource implements PeopleDataSource {
    private List<PhotoEntity> fakePhotoLists = Collections.emptyList();

    @Override
    public Observable<PersonEntity> loadPersonInfo(String personId) {
        return Observable.empty();
    }

    @Override
    public Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId) {
        return loadUserPublicPhotos(userId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId, int page) {
        return Observable.just(fakePhotoLists);
    }

    @VisibleForTesting
    public void setFakePhotoLists(List<PhotoEntity> fakePhotoLists) {
        this.fakePhotoLists = fakePhotoLists;
    }
}
