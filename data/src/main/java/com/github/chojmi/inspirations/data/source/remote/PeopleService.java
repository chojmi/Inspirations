package com.github.chojmi.inspirations.data.source.remote;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.people.PersonEntityImpl;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface PeopleService {

    @GET("services/rest")
    Observable<PersonEntityImpl> loadPersonInfo(@QueryMap Map<String, String> options);

    @GET("services/rest")
    Observable<GalleryEntityImpl> loadUserPublicPhotos(@QueryMap Map<String, String> options);
}
