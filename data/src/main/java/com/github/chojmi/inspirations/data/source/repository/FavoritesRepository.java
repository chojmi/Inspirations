package com.github.chojmi.inspirations.data.source.repository;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.data.source.remote.service.FavoritesService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.domain.repository.FavoritesDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class FavoritesRepository implements FavoritesDataSource {

    private FavoritesService favoritesService;
    private RemoteQueryProducer remoteQueryProducer;

    @Inject
    public FavoritesRepository(@NonNull FavoritesService favoritesService, @NonNull RemoteQueryProducer remoteQueryProducer) {
        this.favoritesService = checkNotNull(favoritesService);
        this.remoteQueryProducer = checkNotNull(remoteQueryProducer);
    }

    @Override
    public Observable<Void> add(String photoId) {
        return favoritesService.add(remoteQueryProducer.produceAddToFavs(photoId));
    }

    @Override
    public Observable<Void> remove(String photoId) {
        return favoritesService.remove(remoteQueryProducer.produceRemoveFromFavs(photoId));
    }
}
