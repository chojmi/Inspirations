package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.domain.usecase.GetUserPublicPhotos;
import com.github.chojmi.inspirations.presentation.gallery.GalleryScope;
import com.github.chojmi.inspirations.presentation.mapper.gallery.PhotoDataMapper;

import dagger.Module;
import dagger.Provides;

@GalleryScope
@Module
public class GridModule {

    @Provides
    GridContract.Presenter provideGalleryPresenter(GetUserPublicPhotos getUserPublicPhotos, PhotoDataMapper photoDataMapper) {
        return new GridPresenter(getUserPublicPhotos, photoDataMapper);
    }
}
