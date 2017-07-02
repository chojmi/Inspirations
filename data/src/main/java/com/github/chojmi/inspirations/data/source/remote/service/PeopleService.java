package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.people.PersonEntityImpl;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface PeopleService {

    @GET("services/rest")
    Flowable<PersonEntityImpl> loadPersonInfo(@QueryMap Map<String, String> options);

    @GET("services/rest")
    Flowable<GalleryEntityImpl> loadUserPublicPhotos(@QueryMap Map<String, String> options);
}
