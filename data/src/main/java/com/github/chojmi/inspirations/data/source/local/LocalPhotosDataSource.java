package com.github.chojmi.inspirations.data.source.local;

import android.content.Context;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import java.util.List;

import io.reactivex.Observable;

import static dagger.internal.Preconditions.checkNotNull;

public final class LocalPhotosDataSource implements PhotosDataSource {

    private Context context;

    public LocalPhotosDataSource(Context context) {
        this.context = checkNotNull(context);
    }

    @Override
    public Observable<List<PersonEntity>> loadPhotoFavs(String photoId) {
        return Observable.empty();
    }
}
