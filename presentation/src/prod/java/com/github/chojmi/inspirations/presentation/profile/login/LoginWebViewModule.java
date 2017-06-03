package com.github.chojmi.inspirations.presentation.profile.login;

import com.github.chojmi.inspirations.domain.usecase.auth.GetFrob;
import com.github.chojmi.inspirations.domain.usecase.auth.GetToken;
import com.github.chojmi.inspirations.presentation.profile.ProfileScope;

import dagger.Module;
import dagger.Provides;

@ProfileScope
@Module
public class LoginWebViewModule {
    @Provides
    LoginWebViewContract.Presenter providePhotoViewPresenter(GetFrob getFrob, GetToken getToken) {
        return new LoginWebViewPresenter(getFrob, getToken);
    }
}