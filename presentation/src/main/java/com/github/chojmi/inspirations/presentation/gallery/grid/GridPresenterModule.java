package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.domain.usecase.GetGallery;

import dagger.Module;
import dagger.Provides;

@Module
class GridPresenterModule {

    @Provides
    public GridContract.Presenter provideGalleryPresenter(GetGallery getGallery) {
        return new GridPresenter(getGallery);
    }
}
