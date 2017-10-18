package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoCommentsEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoFavsEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoInfoEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoSizeListEntityImpl;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;

public class FakePhotosService implements PhotosService {

    @Override
    public Observable<PhotoInfoEntityImpl> loadPhotoInfo(Map<String, String> options) {
        return Observable.empty();
    }

    @Override
    public Observable<PhotoFavsEntityImpl> loadPhotoFavs(@QueryMap Map<String, String> options) {
        return Observable.empty();
    }

    @Override
    public Observable<PhotoCommentsEntityImpl> loadPhotoComments(@QueryMap Map<String, String> options) {
        return Observable.empty();
    }

    @Override
    public Observable<PhotoSizeListEntityImpl> loadPhotoSizes(@QueryMap Map<String, String> options) {
        return Observable.empty();
    }

    @Override
    public Observable<GalleryEntityImpl> loadSearchPhoto(@QueryMap Map<String, String> options) {
        return Observable.empty();
    }

    @Override
    public Observable<Void> addComment(Map<String, String> options) {
        return Observable.empty();
    }
}
