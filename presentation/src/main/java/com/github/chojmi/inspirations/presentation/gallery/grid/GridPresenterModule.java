package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.domain.usecase.GetGallery;

import dagger.Module;
import dagger.Provides;

@Module
public class GridPresenterModule {

    @Provides
    GridContract.Presenter provideGalleryPresenter(GetGallery getGallery) {
        return new GridPresenter(getGallery);
    }
}
