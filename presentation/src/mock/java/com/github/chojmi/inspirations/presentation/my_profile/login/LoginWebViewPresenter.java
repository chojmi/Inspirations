package com.github.chojmi.inspirations.presentation.my_profile.login;

import android.support.annotation.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

public class LoginWebViewPresenter implements LoginWebViewContract.Presenter {
    private LoginWebViewContract.View view;

    @Override
    public void setView(@NonNull LoginWebViewContract.View view) {
        this.view = checkNotNull(view);
        view.loadLoginPage("https://developer.android.com/");
    }

    @Override
    public void destroyView() {
        this.view = null;
    }
}