package com.github.chojmi.inspirations.data.source.local;

import android.content.Context;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

import static dagger.internal.Preconditions.checkNotNull;

public final class LocalGalleriesDataSource implements GalleriesDataSource {

    private Context context;

    public LocalGalleriesDataSource(Context context) {
        this.context = checkNotNull(context);
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
