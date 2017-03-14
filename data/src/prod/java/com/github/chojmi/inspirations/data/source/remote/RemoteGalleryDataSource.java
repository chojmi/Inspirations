package com.github.chojmi.inspirations.data.source.remote;

import android.content.Context;

import com.github.chojmi.inspirations.data.entity.gallery.GalleryEntity;
import com.github.chojmi.inspirations.data.entity.mapper.GalleryEntityDataMapper;
import com.github.chojmi.inspirations.domain.model.Photo;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;
import com.github.chojmi.presentation.data.BuildConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RemoteGalleryDataSource implements GalleryDataSource {

    @Inject
    GalleryService galleryService;
    private GalleryEntityDataMapper galleryEntityDataMapper = new GalleryEntityDataMapper();

    public RemoteGalleryDataSource(Context context) {
        DaggerRemoteGalleryComponent.builder().restClientModule(new RestClientModule(context)).build().inject(this);
    }

    @Override
    public Observable<List<Photo>> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Observable<List<Photo>> loadGallery(String galleryId, int page) {
        Map<String, String> args = new HashMap<>();
        args.put("method", "flickr.galleries.getPhotos");
        args.put("api_key", BuildConfig.API_KEY);
        args.put("format", "json");
        args.put("gallery_id", galleryId);
        args.put("page", String.valueOf(page));
        return galleryService.loadGallery(args)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).map(galleryEntity -> galleryEntityDataMapper.transform(galleryEntity));
    }
}
