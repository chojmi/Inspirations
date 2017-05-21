package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.photos.PhotoFavsEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.response.PhotoCommentsResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface PhotosService {
    @GET("services/rest")
    Observable<PhotoFavsEntityImpl> loadPhotoFavs(@QueryMap Map<String, String> options);

    @GET("services/rest")
    Observable<PhotoCommentsResponse> loadPhotoComments(@QueryMap Map<String, String> options);
}
