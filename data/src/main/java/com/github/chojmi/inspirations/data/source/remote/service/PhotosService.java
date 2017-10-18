package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoCommentsEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoFavsEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoInfoEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoSizeListEntityImpl;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface PhotosService {
    @GET("services/rest")
    Observable<PhotoInfoEntityImpl> loadPhotoInfo(@QueryMap Map<String, String> options);

    @GET("services/rest")
    Observable<PhotoFavsEntityImpl> loadPhotoFavs(@QueryMap Map<String, String> options);

    @GET("services/rest")
    Observable<PhotoCommentsEntityImpl> loadPhotoComments(@QueryMap Map<String, String> options);

    @GET("services/rest")
    Observable<PhotoSizeListEntityImpl> loadPhotoSizes(@QueryMap Map<String, String> options);

    @GET("services/rest")
    Observable<GalleryEntityImpl> loadSearchPhoto(@QueryMap Map<String, String> options);

    @POST("services/rest")
    Observable<Void> addComment(@QueryMap Map<String, String> options);
}
