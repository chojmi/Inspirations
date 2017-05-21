package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.photos.PhotoFavsEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.response.PhotoCommentsResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;

public class FakePhotosService implements PhotosService {

    @Override
    public Observable<PhotoFavsEntityImpl> loadPhotoFavs(@QueryMap Map<String, String> options) {
        return Observable.empty();
    }

    @Override
    public Observable<PhotoCommentsResponse> loadPhotoComments(@QueryMap Map<String, String> options) {
        return Observable.empty();
    }
}
