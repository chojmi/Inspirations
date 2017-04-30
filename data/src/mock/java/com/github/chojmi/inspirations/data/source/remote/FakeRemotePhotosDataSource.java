package com.github.chojmi.inspirations.data.source.remote;

import android.support.annotation.VisibleForTesting;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class FakeRemotePhotosDataSource implements PhotosDataSource {
    private List<PersonEntity> fakeFavList = Collections.emptyList();


    @VisibleForTesting
    public void setFakePhotoLists(List<PersonEntity> fakeFavList) {
        this.fakeFavList = fakeFavList;
    }

    @Override
    public Observable<List<PersonEntity>> loadPhotoFavs(String photoId) {
        return Observable.just(fakeFavList);
    }
}
