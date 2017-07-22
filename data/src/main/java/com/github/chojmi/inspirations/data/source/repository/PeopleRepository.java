package com.github.chojmi.inspirations.data.source.repository;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class PeopleRepository implements PeopleDataSource {

    private final PeopleDataSource peopleRemoteDataSource;

    private final PeopleDataSource peopleLocalDataSource;

    @Inject
    PeopleRepository(@Remote @NonNull PeopleDataSource peopleRemoteDataSource,
                     @Local @NonNull PeopleDataSource peopleLocalDataSource) {
        this.peopleRemoteDataSource = checkNotNull(peopleRemoteDataSource);
        this.peopleLocalDataSource = checkNotNull(peopleLocalDataSource);
    }

    @Override
    public Observable<PersonEntity> loadPersonInfo(String personId) {
        return Observable.concat(peopleLocalDataSource.loadPersonInfo(personId),
                peopleRemoteDataSource.loadPersonInfo(personId))
                .firstElement()
                .toObservable();
    }

    @Override
    public Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId) {
        return loadUserPublicPhotos(userId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId, int page) {
        return Observable.concat(peopleLocalDataSource.loadUserPublicPhotos(userId, page),
                peopleRemoteDataSource.loadUserPublicPhotos(userId, page))
                .filter(photos -> photos.size() > 0).firstElement()
                .toObservable();
    }
}
