package com.github.chojmi.inspirations.presentation.photo;

import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoInfo;
import com.github.chojmi.inspirations.presentation.common.FavToggler;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDetailsMapper;

import dagger.Module;
import dagger.Provides;

@PhotoScope
@Module
public class PhotoViewModule {

    @Provides
    PhotoViewContract.Presenter providePhotoViewPresenter(PhotoViewActivity activity, GetPhotoFavs getPhotoFavs, GetPhotoInfo getPhotoInfo,
                                                          GetUserInfo getUserInfo, FavToggler favToggler, PhotoDetailsMapper photoDetailsMapper,
                                                          PhotoDataMapper photoDataMapper) {
        return new PhotoViewPresenter(activity.getPhoto(), getPhotoFavs, getPhotoInfo, getUserInfo,
                favToggler, photoDetailsMapper, photoDataMapper);
    }
}
