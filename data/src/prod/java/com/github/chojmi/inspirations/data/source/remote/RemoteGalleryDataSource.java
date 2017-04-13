package com.github.chojmi.inspirations.data.source.remote;

import android.content.Context;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.gallery.GalleryEntity;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;
import com.github.chojmi.presentation.data.BuildConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RemoteGalleryDataSource implements GalleryDataSource {

    @Inject
    GalleryService galleryService;

    public RemoteGalleryDataSource(Context context) {
        DaggerRemoteGalleryComponent.builder().restClientModule(new RestClientModule(context)).build().inject(this);
    }

    @Override
    public Observable<List<PhotoEntity>> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadGallery(String galleryId, int page) {
        Map<String, String> args = new HashMap<>();
        args.put("method", "flickr.galleries.getPhotos");
        args.put("api_key", BuildConfig.API_KEY);
        args.put("format", "json");
        args.put("gallery_id", galleryId);
        args.put("page", String.valueOf(page));
        return galleryService.loadGallery(args)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).map(GalleryEntity::getPhoto);
    }

    @Override
    public Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId) {
        return loadUserPublicPhotos(userId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId, int page) {
        Map<String, String> args = new HashMap<>();
        args.put("method", "flickr.people.getPublicPhotos");
        args.put("api_key", BuildConfig.API_KEY);
        args.put("format", "json");
        args.put("user_id", userId);
        args.put("page", String.valueOf(page));
        return galleryService.loadGallery(args)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).map(GalleryEntity::getPhoto);
    }
}
