package com.github.chojmi.inspirations.presentation.gallery;

import com.github.chojmi.inspirations.domain.usecase.GetGallery;

import dagger.Module;
import dagger.Provides;

@Module
class GalleryPresenterModule {

    @Provides
    public GalleryContract.Presenter provideGalleryPresenter(GetGallery getGallery) {
        return new GalleryPresenter(getGallery);
    }
}
