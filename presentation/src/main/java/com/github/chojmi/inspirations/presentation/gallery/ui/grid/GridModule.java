package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoInfo;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoSizeList;
import com.github.chojmi.inspirations.presentation.common.FavTogglerImpl;
import com.github.chojmi.inspirations.presentation.common.compound_usecase.GetPhotosCompoundUseCase;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDetailsMapper;
import com.github.chojmi.inspirations.presentation.gallery.ui.GalleryScope;

import dagger.Module;
import dagger.Provides;

@GalleryScope
@Module
public class GridModule {

    @Provides
    GridPhotoContract.Presenter provideGridPhotoPresenter(GetPhotosCompoundUseCase getPhotosCompoundUseCase, FavTogglerImpl favToggler) {
        return new GridPhotoPresenter(getPhotosCompoundUseCase, favToggler);
    }

    @Provides
    GridPhotoAttrsContract.Presenter provideGridPhotoAttrsPresenter(GetPhotoFavs getPhotoFavs, GetPhotoInfo getPhotoInfo, GetPhotoSizeList getPhotoSizeList, PhotoDetailsMapper photoDetailsMapper) {
        return new GridPhotoAttrsPresenter(getPhotoFavs, getPhotoInfo, getPhotoSizeList, photoDetailsMapper);
    }

    @Provides
    GetPhotosCompoundUseCase provideGetPhotosCompoundUseCase(GetUserPublicPhotos getUserPublicPhotos, GetUserInfo getUserInfo, PhotoDataMapper photoDataMapper) {
        return new GetPhotosCompoundUseCase(getUserPublicPhotos, getUserInfo, photoDataMapper);
    }
}
