package com.github.chojmi.inspirations.presentation.my_profile.login;

import com.github.chojmi.inspirations.domain.usecase.auth.GetFrob;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

public class LoginWebViewPresenter implements LoginWebViewContract.Presenter {
    private final GetFrob getFrob;
    private final CompositeDisposable disposables;
    private LoginWebViewContract.View view;

    public LoginWebViewPresenter(@NonNull GetFrob getFrob) {
        this.getFrob = checkNotNull(getFrob);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void setView(@NonNull LoginWebViewContract.View view) {
        this.view = checkNotNull(view);
        disposables.add(getFrob.buildUseCaseObservable(GetFrob.SubmitEvent.createObservable()).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSuccess()) {
                view.loadLoginPage(submitUiModel.getResult().getLoginUrl());
            }
        }, Timber::e));
    }

    @Override
    public void destroyView() {
        this.disposables.dispose();
        this.view = null;
    }
}