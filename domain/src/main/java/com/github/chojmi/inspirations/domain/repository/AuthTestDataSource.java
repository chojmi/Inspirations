package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.people.UserEntity;

import io.reactivex.Flowable;

public interface AuthTestDataSource {
    Flowable<UserEntity> getLoginData();
}
