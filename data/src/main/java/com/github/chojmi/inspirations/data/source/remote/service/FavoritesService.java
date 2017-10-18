package com.github.chojmi.inspirations.data.source.remote.service;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface FavoritesService {
    @POST("services/rest")
    Observable<Void> add(@QueryMap Map<String, String> options);

    @POST("services/rest")
    Observable<Void> remove(@QueryMap Map<String, String> options);
}
