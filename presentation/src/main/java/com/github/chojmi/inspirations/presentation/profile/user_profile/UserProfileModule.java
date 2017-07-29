package com.github.chojmi.inspirations.presentation.profile.user_profile;

import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.presentation.profile.ProfileScope;
import com.github.chojmi.inspirations.presentation.profile.user_profile.public_photos.UserPublicPhotosContract;
import com.github.chojmi.inspirations.presentation.profile.user_profile.public_photos.UserPublicPhotosPresenter;

import dagger.Module;
import dagger.Provides;
import dagger.internal.Preconditions;
import io.reactivex.annotations.NonNull;

@ProfileScope
@Module
public class UserProfileModule {
    private final String userId;

    public UserProfileModule(@NonNull String userId) {
        this.userId = Preconditions.checkNotNull(userId);
    }

    @Provides
    UserProfileContract.Presenter provideUserProfilePresenter(GetUserInfo getUserInfo) {
        return new UserProfilePresenter(getUserInfo, userId);
    }

    @Provides
    UserPublicPhotosContract.Presenter provideUserPublicPhotosPresenter(GetUserPublicPhotos getUserPublicPhotos) {
        return new UserPublicPhotosPresenter(getUserPublicPhotos, userId);
    }
}