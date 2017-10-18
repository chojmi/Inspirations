package com.github.chojmi.inspirations.data.source.remote.service;

import java.util.Map;

import io.reactivex.Observable;

public class FakeFavoritesService implements FavoritesService {
    @Override
    public Observable<Void> add(Map<String, String> options) {
        return Observable.empty();
    }

    @Override
    public Observable<Void> remove(Map<String, String> options) {
        return Observable.empty();
    }
}
