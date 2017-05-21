package com.github.chojmi.inspirations.data.source.remote.test_service;

import com.github.chojmi.inspirations.data.entity.photos.FakePhotoCommentsEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.FakePhotoFavsEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoCommentsEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoFavsEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.service.PhotosService;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;

public final class FakePhotosService implements PhotosService {

    private final FakePhotoFavsEntityImpl fakePhotoFavsEntity;
    private final FakePhotoCommentsEntityImpl fakePhotoCommentsEntity;
    private final Map<String, String> loadPhotoFavsQuery, loadPhotoCommentsQuery;

    public FakePhotosService(Map<String, String> loadPhotoFavsQuery, Map<String, String> loadPhotoCommentsQuery) {
        this.loadPhotoFavsQuery = loadPhotoFavsQuery;
        this.loadPhotoCommentsQuery = loadPhotoCommentsQuery;
        this.fakePhotoFavsEntity = new FakePhotoFavsEntityImpl();
        this.fakePhotoCommentsEntity = new FakePhotoCommentsEntityImpl();
    }

    @Override
    public Observable<PhotoFavsEntityImpl> loadPhotoFavs(@QueryMap Map<String, String> options) {
        if (loadPhotoCommentsQuery.equals(options)) {
            return Observable.just(fakePhotoFavsEntity);
        } else {
            return Observable.error(new Throwable("Wrong args"));
        }
    }

    @Override
    public Observable<PhotoCommentsEntityImpl> loadPhotoComments(@QueryMap Map<String, String> options) {
        if (loadPhotoCommentsQuery.equals(options)) {
            return Observable.just(fakePhotoCommentsEntity);
        } else {
            return Observable.error(new Throwable("Wrong args"));
        }
    }

    public FakePhotoFavsEntityImpl getLoadPhotoFavsResult() {
        return fakePhotoFavsEntity;
    }

    public FakePhotoCommentsEntityImpl getLoadPhotoCommentsResult() {
        return fakePhotoCommentsEntity;
    }
}