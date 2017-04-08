package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.domain.usecase.GetGallery;
import com.github.chojmi.inspirations.presentation.mapper.gallery.PhotoDataMapper;

import dagger.Module;
import dagger.Provides;

@Module
public class GalleryModule {

    @Provides
    GridContract.Presenter provideGalleryPresenter(GetGallery getGallery, PhotoDataMapper photoDataMapper) {
        return new GridPresenter(getGallery, photoDataMapper);
    }
}
