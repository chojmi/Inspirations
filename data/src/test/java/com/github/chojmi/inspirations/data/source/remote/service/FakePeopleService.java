package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.FakeGalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.people.FakePersonEntityImpl;
import com.github.chojmi.inspirations.data.entity.people.PersonEntityImpl;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;

public final class FakePeopleService implements PeopleService {

    private final FakePersonEntityImpl fakePersonEntity;
    private final FakeGalleryEntityImpl fakeGalleryEntity;
    private final Map<String, String> loadPersonInfoQuery, loadUserPublicPhotosQuery;

    public FakePeopleService(Map<String, String> loadPersonInfoQuery, Map<String, String> loadUserPublicPhotosQuery) {
        this.loadPersonInfoQuery = loadPersonInfoQuery;
        this.loadUserPublicPhotosQuery = loadUserPublicPhotosQuery;
        this.fakePersonEntity = new FakePersonEntityImpl();
        this.fakeGalleryEntity = new FakeGalleryEntityImpl();
    }

    @Override
    public Observable<PersonEntityImpl> loadPersonInfo(@QueryMap Map<String, String> options) {
        if (loadPersonInfoQuery.equals(options)) {
            return Observable.just(fakePersonEntity);
        } else {
            return Observable.error(new Throwable("Wrong args"));
        }
    }

    @Override
    public Observable<GalleryEntityImpl> loadUserPublicPhotos(@QueryMap Map<String, String> options) {
        if (loadUserPublicPhotosQuery.equals(options)) {
            return Observable.just(fakeGalleryEntity);
        } else {
            return Observable.error(new Throwable("Wrong args"));
        }
    }

    public FakePersonEntityImpl getLoadPersonInfoResult() {
        return fakePersonEntity;
    }

    public FakeGalleryEntityImpl getLoadUserPublicPhotosResult() {
        return fakeGalleryEntity;
    }
}
