package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.people.UserEntity;

import io.reactivex.Observable;

public interface AuthTestDataSource {
    Observable<UserEntity> getLoginData();
}
