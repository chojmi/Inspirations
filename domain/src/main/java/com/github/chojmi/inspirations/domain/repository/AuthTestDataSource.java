package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;

import io.reactivex.Observable;

public interface AuthTestDataSource {
    Observable<PersonEntity> getLoginData();
}
