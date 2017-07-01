package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.people.PersonEntityImpl;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;

public class FakePeopleService implements PeopleService {

    @Override
    public Observable<PersonEntityImpl> loadPersonInfo(@QueryMap Map<String, String> options) {
        return Observable.empty();
    }

    @Override
    public Observable<GalleryEntityImpl> loadUserPublicPhotos(@QueryMap Map<String, String> options) {
        return Observable.empty();
    }
}
