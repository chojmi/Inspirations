package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoComments;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.GalleryScope;
import com.github.chojmi.inspirations.presentation.mapper.gallery.GalleryAttrsMapper;
import com.github.chojmi.inspirations.presentation.mapper.gallery.PhotoDataMapper;

import dagger.Module;
import dagger.Provides;

@GalleryScope
@Module
public class GridModule {

    @Provides
    GridPhotoContract.Presenter provideGridPhotoPresenter(GetUserPublicPhotos getUserPublicPhotos, GetUserInfo getUserInfo, PhotoDataMapper photoDataMapper) {
        return new GridPhotoPresenter(getUserPublicPhotos, getUserInfo, photoDataMapper);
    }

    @Provides
    GridPhotoAttrsContract.Presenter provideGridPhotoAttrsPresenter(GetPhotoFavs getPhotoFavs, GetPhotoComments getPhotoComments, GalleryAttrsMapper galleryAttrsMapper) {
        return new GridPhotoAttrsPresenter(getPhotoFavs, getPhotoComments, galleryAttrsMapper);
    }
}
