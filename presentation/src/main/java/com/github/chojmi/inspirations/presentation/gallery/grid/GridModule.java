package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.presentation.gallery.GalleryScope;
import com.github.chojmi.inspirations.presentation.mapper.gallery.PhotoDataMapper;

import dagger.Module;
import dagger.Provides;

@GalleryScope
@Module
public class GridModule {

    @Provides
    GridContract.Presenter provideGalleryPresenter(GetUserPublicPhotos getUserPublicPhotos, GetUserInfo getUserInfo, PhotoDataMapper photoDataMapper) {
        return new GridPresenter(getUserPublicPhotos, getUserInfo, photoDataMapper);
    }
}
