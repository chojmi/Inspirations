package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.photos.PersonEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.response.PhotoCommentsResponse;
import com.github.chojmi.inspirations.data.source.remote.service.PhotosService;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;

public class FakePhotosService implements PhotosService {

    @Override
    public Observable<List<PersonEntityImpl>> loadPhotoFavs(@QueryMap Map<String, String> options) {
        return Observable.empty();
    }

    @Override
    public Observable<PhotoCommentsResponse> loadPhotoComments(@QueryMap Map<String, String> options) {
        return Observable.empty();
    }
}
