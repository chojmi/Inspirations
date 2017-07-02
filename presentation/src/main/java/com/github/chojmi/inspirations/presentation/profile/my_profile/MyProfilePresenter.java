package com.github.chojmi.inspirations.presentation.profile.my_profile;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.usecase.auth.GetAccessToken;
import com.github.chojmi.inspirations.domain.usecase.auth.GetLoginData;
import com.github.scribejava.core.model.OAuth1AccessToken;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.ResourceSubscriber;
import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

public class MyProfilePresenter implements MyProfileContract.Presenter {
    private final GetAccessToken getToken;
    private final GetLoginData getLoginData;
    private CompositeDisposable disposables;
    private MyProfileContract.View view;

    public MyProfilePresenter(@NonNull GetLoginData getLoginData, @NonNull GetAccessToken getToken) {
        this.getLoginData = checkNotNull(getLoginData);
        this.getToken = checkNotNull(getToken);
    }

    @Override
    public void setView(@NonNull MyProfileContract.View view) {
        this.view = checkNotNull(view);
        this.disposables = new CompositeDisposable();
        fetchToken();
    }

    @Override
    public void destroyView() {
        this.disposables.clear();
        this.view = null;
    }

    @Override
    public void loginSuccessful() {
        fetchToken();
    }

    private void fetchProfile(OAuth1AccessToken tokenEntity) {
        //TODO: Fetch all profile data
        view.renderProfile("Zalogowano");
        disposables.add(getLoginData.process().subscribe(uiModel -> {
            if (uiModel.isInProgress()) {
                return;
            }
            if (uiModel.isSucceed()) {
                view.renderProfile("Zalogowano jako: " + uiModel.getResult().getUsername());
            }
        }, Timber::d));
    }

    private void fetchToken() {
        ResourceSubscriber<SubmitUiModel<OAuth1AccessToken>> tokenObserver = new ResourceSubscriber<SubmitUiModel<OAuth1AccessToken>>() {
            OAuth1AccessToken tokenEntity = null;

            @Override
            public void onNext(SubmitUiModel<OAuth1AccessToken> submitUiModel) {
                tokenEntity = submitUiModel.getResult();
            }

            @Override
            public void onError(Throwable t) {
                Timber.e(t);
            }

            @Override
            public void onComplete() {
                if (view == null) {
                    return;
                }
                if (tokenEntity != null) {
                    fetchProfile(tokenEntity);
                }
                view.isLoggedIn(tokenEntity != null);
            }
        };
        disposables.add(tokenObserver);
        getToken.process().subscribe(tokenObserver);
    }
}
