package com.github.chojmi.inspirations.data.source.remote;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.remote.service.GalleriesService;
import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class RemoteGalleriesDataSource extends BaseRemoteDataSource implements GalleriesDataSource {

    @Inject
    GalleriesService galleryService;

    public RemoteGalleriesDataSource(Context context) {
        DaggerRemoteComponent.builder().restClientModule(new RestClientModule(context)).build().inject(this);
    }

    @Override
    public Observable<List<PhotoEntity>> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadGallery(String galleryId, int page) {
        Map<String, String> args = getBaseArgs("flickr.galleries.getPhotos");
        args.put("gallery_id", galleryId);
        args.put("page", String.valueOf(page));
        return galleryService.loadGallery(args)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).map(GalleryEntity::getPhoto);
    }
}
