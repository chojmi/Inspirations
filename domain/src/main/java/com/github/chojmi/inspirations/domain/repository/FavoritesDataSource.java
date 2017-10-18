package com.github.chojmi.inspirations.domain.repository;

import io.reactivex.Observable;

public interface FavoritesDataSource {
    Observable<Void> add(String photoId);

    Observable<Void> remove(String photoId);
}
