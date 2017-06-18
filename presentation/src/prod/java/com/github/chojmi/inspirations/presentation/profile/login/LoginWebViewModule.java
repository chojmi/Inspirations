package com.github.chojmi.inspirations.presentation.profile.login;

import com.github.chojmi.inspirations.domain.usecase.auth.GetAccessToken;
import com.github.chojmi.inspirations.domain.usecase.auth.GetAuthorizationUrl;
import com.github.chojmi.inspirations.presentation.profile.ProfileScope;

import dagger.Module;
import dagger.Provides;

@ProfileScope
@Module
public class LoginWebViewModule {
    @Provides
    LoginWebViewContract.Presenter providePhotoViewPresenter(GetAuthorizationUrl getAuthorizationUrl, GetAccessToken getToken) {
        return new LoginWebViewPresenter(getAuthorizationUrl, getToken);
    }
}