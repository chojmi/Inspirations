package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;

import java.util.List;

import io.reactivex.Observable;

public interface PeopleDataSource {
    Observable<PersonEntity> loadPersonInfo(String personId);

    Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId);

    Observable<List<PhotoEntity>> loadUserPublicPhotos(String userId, int page);
}
