package com.github.chojmi.inspirations.data.source.local;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LocalPeopleDataSource implements PeopleDataSource {

    @Inject
    public LocalPeopleDataSource() {
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
        return Observable.empty();
    }
}
