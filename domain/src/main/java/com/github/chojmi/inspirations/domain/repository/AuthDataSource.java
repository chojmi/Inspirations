package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.auth.FrobEntity;

import io.reactivex.Observable;

public interface AuthDataSource {
    Observable<FrobEntity> getFrob();
}
