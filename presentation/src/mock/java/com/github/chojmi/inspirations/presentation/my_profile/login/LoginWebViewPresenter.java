package com.github.chojmi.inspirations.presentation.my_profile.login;

import android.support.annotation.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

public class LoginWebViewPresenter implements LoginWebViewContract.Presenter {
    private LoginWebViewContract.View view;

    @Override
    public void setView(@NonNull LoginWebViewContract.View view) {
        this.view = checkNotNull(view);
        this.view.closeSuccessfully();
    }

    @Override
    public void destroyView() {
        this.view = null;
    }

    @Override
    public void pageStartedLoading(String url) {

    }

    @Override
    public boolean isPermittedUrl(String url) {
        return false;
    }
}