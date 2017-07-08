package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;

import java.util.List;

import io.reactivex.Flowable;

public interface PeopleDataSource {
    Flowable<PersonEntity> loadPersonInfo(String personId);

    Flowable<List<PhotoEntity>> loadUserPublicPhotos(String userId);

    Flowable<List<PhotoEntity>> loadUserPublicPhotos(String userId, int page);
}
