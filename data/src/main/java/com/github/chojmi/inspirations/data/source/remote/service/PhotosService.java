package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoCommentsEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoFavsEntityImpl;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface PhotosService {
    @GET("services/rest")
    Observable<PhotoFavsEntityImpl> loadPhotoFavs(@QueryMap Map<String, String> options);

    @GET("services/rest")
    Observable<PhotoCommentsEntityImpl> loadPhotoComments(@QueryMap Map<String, String> options);

    @GET("services/rest")
    Observable<GalleryEntityImpl> loadSearchPhoto(@QueryMap Map<String, String> options);

}
