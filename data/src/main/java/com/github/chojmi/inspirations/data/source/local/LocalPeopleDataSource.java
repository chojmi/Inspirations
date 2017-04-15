package com.github.chojmi.inspirations.data.source.local;

import android.content.Context;

import com.github.chojmi.inspirations.domain.entity.gallery.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

import static dagger.internal.Preconditions.checkNotNull;

public final class LocalPeopleDataSource implements PeopleDataSource {
    private Context context;

    public LocalPeopleDataSource(Context context) {
        this.context = checkNotNull(context);
    }

    @Override
    public Observable<PersonEntity> loadPersonInfo(String personId) {
        return Observable.empty();
    }


    @Override
    public Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId) {
        return loadUserPublicPhotos(userId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId, int page) {
        return Observable.just(Collections.emptyList());
    }
}
