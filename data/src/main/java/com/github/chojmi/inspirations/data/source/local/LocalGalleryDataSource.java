package com.github.chojmi.inspirations.data.source.local;

import android.content.Context;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class LocalGalleryDataSource implements GalleryDataSource {

    private Context context;

    public LocalGalleryDataSource(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<PhotoEntity>> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadGallery(String galleryId, int page) {
        return Observable.just(Collections.emptyList());
    }
}
