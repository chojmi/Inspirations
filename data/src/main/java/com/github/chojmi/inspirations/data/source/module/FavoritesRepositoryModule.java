package com.github.chojmi.inspirations.data.source.module;

import com.github.chojmi.inspirations.data.source.repository.FavoritesRepository;
import com.github.chojmi.inspirations.domain.repository.FavoritesDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class FavoritesRepositoryModule {

    @Singleton
    @Provides
    FavoritesDataSource provideFavoritesDataSource(FavoritesRepository favoritesRepository) {
        return favoritesRepository;
    }
}
