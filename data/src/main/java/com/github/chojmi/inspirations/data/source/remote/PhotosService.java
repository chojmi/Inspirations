package com.github.chojmi.inspirations.data.source.remote;

import com.github.chojmi.inspirations.data.entity.photos.PersonEntityImpl;
import com.github.chojmi.inspirations.data.source.repository.response.PhotoCommentsResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface PhotosService {
    @GET("services/rest")
    Observable<List<PersonEntityImpl>> loadPhotoFavs(@QueryMap Map<String, String> options);

    @GET("services/rest")
    Observable<PhotoCommentsResponse> loadPhotoComments(@QueryMap Map<String, String> options);
}
