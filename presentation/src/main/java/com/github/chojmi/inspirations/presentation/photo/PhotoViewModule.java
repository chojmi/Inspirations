package com.github.chojmi.inspirations.presentation.photo;

import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoComments;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDetailsMapper;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;

import dagger.Module;
import dagger.Provides;

@PhotoScope
@Module
public class PhotoViewModule {
    private final Photo photo;

    public PhotoViewModule(Photo photo) {
        this.photo = photo;
    }

    @Provides
    PhotoViewContract.Presenter providePhotoViewPresenter(GetPhotoFavs getPhotoFavs, GetPhotoComments getPhotoComments,
                                                          GetUserInfo getUserInfo, PhotoDetailsMapper photoDetailsMapper,
                                                          PhotoDataMapper photoDataMapper) {
        return new PhotoViewPresenter(photo, getPhotoFavs, getPhotoComments, getUserInfo, photoDetailsMapper, photoDataMapper);
    }
}
