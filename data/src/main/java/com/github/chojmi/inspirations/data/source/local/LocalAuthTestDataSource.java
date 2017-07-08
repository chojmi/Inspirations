package com.github.chojmi.inspirations.data.source.local;

import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.repository.AuthTestDataSource;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class LocalAuthTestDataSource implements AuthTestDataSource {
    @Inject
    public LocalAuthTestDataSource() {
    }

    @Override
    public Flowable<UserEntity> getLoginData() {
        return Flowable.empty();
    }
}
