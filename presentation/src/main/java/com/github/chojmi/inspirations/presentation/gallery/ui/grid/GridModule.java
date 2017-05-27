package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoComments;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.compound_usecase.GetPhotosCompoundUseCase;
import com.github.chojmi.inspirations.presentation.gallery.mapper.GalleryAttrsMapper;
import com.github.chojmi.inspirations.presentation.gallery.mapper.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.gallery.ui.GalleryScope;

import dagger.Module;
import dagger.Provides;

@GalleryScope
@Module
public class GridModule {

    @Provides
    GridPhotoContract.Presenter provideGridPhotoPresenter(GetPhotosCompoundUseCase getPhotosCompoundUseCase) {
        return new GridPhotoPresenter(getPhotosCompoundUseCase);
    }

    @Provides
    GridPhotoAttrsContract.Presenter provideGridPhotoAttrsPresenter(GetPhotoFavs getPhotoFavs, GetPhotoComments getPhotoComments, GalleryAttrsMapper galleryAttrsMapper) {
        return new GridPhotoAttrsPresenter(getPhotoFavs, getPhotoComments, galleryAttrsMapper);
    }

    @Provides
    GetPhotosCompoundUseCase provideGetPhotosCompoundUseCase(GetUserPublicPhotos getUserPublicPhotos, GetUserInfo getUserInfo, PhotoDataMapper photoDataMapper) {
        return new GetPhotosCompoundUseCase(getUserPublicPhotos, getUserInfo, photoDataMapper);
    }
}
