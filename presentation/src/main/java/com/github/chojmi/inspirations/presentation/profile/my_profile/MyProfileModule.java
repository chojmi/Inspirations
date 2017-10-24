package com.github.chojmi.inspirations.presentation.profile.my_profile;

import com.github.chojmi.inspirations.domain.usecase.auth.GetAccessToken;
import com.github.chojmi.inspirations.domain.usecase.auth.GetLoginData;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.profile.ProfileScope;
import com.github.chojmi.inspirations.presentation.profile.user_profile.UserPublicPhotosContract;
import com.github.chojmi.inspirations.presentation.profile.user_profile.UserPublicPhotosPresenter;

import dagger.Module;
import dagger.Provides;

@ProfileScope
@Module
public class MyProfileModule {
    @Provides
    MyProfileContract.Presenter provideProfilePresenter(GetLoginData getLoginData, GetAccessToken getToken) {
        return new MyProfilePresenter(getLoginData, getToken);
    }

    @Provides
    UserPublicPhotosContract.Presenter provideUserPublicPhotosPresenter(GetUserPublicPhotos getUserPublicPhotos, PhotoDataMapper photoDataMapper) {
        return new UserPublicPhotosPresenter(getUserPublicPhotos, photoDataMapper);
    }
}