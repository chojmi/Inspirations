package com.github.chojmi.inspirations.presentation.my_profile.login;

import com.github.chojmi.inspirations.domain.usecase.auth.GetFrob;
import com.github.chojmi.inspirations.domain.usecase.auth.GetToken;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

public class LoginWebViewPresenter implements LoginWebViewContract.Presenter {
    private final GetFrob getFrob;
    private final GetToken getToken;
    private final CompositeDisposable disposables;
    private LoginWebViewContract.View view;
    private String currentFrob = "";

    public LoginWebViewPresenter(@NonNull GetFrob getFrob, @NonNull GetToken getToken) {
        this.getFrob = checkNotNull(getFrob);
        this.getToken = checkNotNull(getToken);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void setView(@NonNull LoginWebViewContract.View view) {
        this.view = checkNotNull(view);
        fetchFrob();
    }

    private void fetchFrob() {
        disposables.add(getFrob.buildUseCaseObservable(GetFrob.SubmitEvent.createObservable()).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSuccess()) {
                this.currentFrob = submitUiModel.getResult().getFrob();
                view.loadLoginPage(submitUiModel.getResult().getLoginUrl());
            }
        }, Timber::e));
    }

    private void fetchToken(String frob) {
        disposables.add(getToken.buildUseCaseObservable(GetToken.SubmitEvent.createObservable(frob)).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSuccess()) {
                view.closeSuccessfully();
            }
        }, Timber::e));
    }

    @Override
    public void destroyView() {
        this.getFrob.dispose();
        this.getToken.dispose();
        this.disposables.dispose();
        this.view = null;
    }

    @Override
    public void pageStartedLoading(String url) {
        if (isLogged(url)) {
            fetchToken(currentFrob);
        }
    }

    private boolean isLogged(String url) {
        return "https://www.flickr.com/services/auth/" .equals(url);
    }

    @Override
    public boolean isPermittedUrl(String url) {
        return true;
    }
}