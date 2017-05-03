package com.github.chojmi.inspirations.data.source.remote.test_service;

import com.github.chojmi.inspirations.data.entity.photos.FakePhotoCommentsResponse;
import com.github.chojmi.inspirations.data.entity.photos.PersonEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.response.PhotoCommentsResponse;
import com.github.chojmi.inspirations.data.source.remote.service.PhotosService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;

public final class FakePhotosService implements PhotosService {

    private final List<PersonEntityImpl> fakePhotoFavs;
    private final PhotoCommentsResponse fakePhotoCommentsResponse;
    private final Map<String, String> loadPhotoFavsQuery, loadPhotoCommentsQuery;

    public FakePhotosService(Map<String, String> loadPhotoFavsQuery, Map<String, String> loadPhotoCommentsQuery) {
        this.loadPhotoFavsQuery = loadPhotoFavsQuery;
        this.loadPhotoCommentsQuery = loadPhotoCommentsQuery;
        this.fakePhotoFavs = new ArrayList<>();
        this.fakePhotoCommentsResponse = new FakePhotoCommentsResponse();
    }

    @Override
    public Observable<List<PersonEntityImpl>> loadPhotoFavs(@QueryMap Map<String, String> options) {
        if (loadPhotoCommentsQuery.equals(options)) {
            return Observable.just(fakePhotoFavs);
        } else {
            return Observable.error(new Throwable("Wrong args"));
        }
    }

    @Override
    public Observable<PhotoCommentsResponse> loadPhotoComments(@QueryMap Map<String, String> options) {
        if (loadPhotoCommentsQuery.equals(options)) {
            return Observable.just(fakePhotoCommentsResponse);
        } else {
            return Observable.error(new Throwable("Wrong args"));
        }
    }

    public List<PersonEntityImpl> getLoadPhotoFavsResult() {
        return fakePhotoFavs;
    }

    public PhotoCommentsResponse getLoadPhotoCommentsResult() {
        return fakePhotoCommentsResponse;
    }
}