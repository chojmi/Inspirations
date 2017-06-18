package com.github.chojmi.inspirations.presentation.profile.my_profile;

import com.github.chojmi.inspirations.domain.usecase.auth.GetToken;
import com.github.chojmi.inspirations.presentation.profile.ProfileScope;

import dagger.Module;
import dagger.Provides;

@ProfileScope
@Module
public class MyProfileModule {
    @Provides
    MyProfileContract.Presenter providePhotoViewPresenter(GetToken getToken) {
        return new MyProfilePresenter(getToken);
    }
}