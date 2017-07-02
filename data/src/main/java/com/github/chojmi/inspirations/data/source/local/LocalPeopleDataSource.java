package com.github.chojmi.inspirations.data.source.local;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class LocalPeopleDataSource implements PeopleDataSource {

    @Inject
    public LocalPeopleDataSource() {
    }

    @Override
    public Flowable<PersonEntity> loadPersonInfo(String personId) {
        return Flowable.empty();
    }


    @Override
    public Flowable<List<PhotoEntity>> loadUserPublicPhotos(String userId) {
        return loadUserPublicPhotos(userId, 1);
    }

    @Override
    public Flowable<List<PhotoEntity>> loadUserPublicPhotos(String userId, int page) {
        return Flowable.just(Collections.emptyList());
    }
}
