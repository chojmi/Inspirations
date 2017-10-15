package com.github.chojmi.inspirations.presentation.profile.user_profile;

import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.profile.ProfileScope;

import dagger.Module;
import dagger.Provides;

@ProfileScope
@Module
public class UserProfileModule {

    @Provides
    UserProfileContract.Presenter provideUserProfilePresenter(UserProfileActivity activity, GetUserInfo getUserInfo) {
        return new UserProfilePresenter(getUserInfo, activity.getArgUserId());
    }

    @Provides
    UserPublicPhotosContract.Presenter provideUserPublicPhotosPresenter(UserProfileActivity activity, GetUserPublicPhotos getUserPublicPhotos, PhotoDataMapper photoDataMapper) {
        return new UserPublicPhotosPresenter(getUserPublicPhotos, photoDataMapper, activity.getArgUserId());
    }
}