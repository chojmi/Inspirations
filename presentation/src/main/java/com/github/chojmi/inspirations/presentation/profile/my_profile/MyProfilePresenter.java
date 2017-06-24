package com.github.chojmi.inspirations.presentation.profile.my_profile;

import com.github.chojmi.inspirations.domain.usecase.auth.GetAccessToken;
import com.github.chojmi.inspirations.domain.usecase.auth.GetLoginData;
import com.github.scribejava.core.model.OAuth1AccessToken;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

public class MyProfilePresenter implements MyProfileContract.Presenter {
    private final GetAccessToken getToken;
    private final GetLoginData getLoginData;
    private final CompositeDisposable disposables;
    private MyProfileContract.View view;

    public MyProfilePresenter(@NonNull GetLoginData getLoginData, @NonNull GetAccessToken getToken) {
        this.getLoginData = checkNotNull(getLoginData);
        this.getToken = checkNotNull(getToken);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void setView(@NonNull MyProfileContract.View view) {
        this.view = checkNotNull(view);
        fetchToken();
    }

    @Override
    public void destroyView() {
        this.getToken.dispose();
        this.disposables.dispose();
        this.view = null;
    }

    @Override
    public void loginSuccessful() {
        fetchToken();
    }

    private Observer<GetAccessToken.SubmitUiModel> getTokenObserver() {
        return new DisposableObserver<GetAccessToken.SubmitUiModel>() {
            OAuth1AccessToken tokenEntity = null;

            @Override
            public void onNext(GetAccessToken.SubmitUiModel submitUiModel) {
                tokenEntity = submitUiModel.getResult();
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
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
    }

    private void fetchProfile(OAuth1AccessToken tokenEntity) {
        //TODO: Fetch all profile data
        view.renderProfile("Zalogowano");
        getLoginData.buildUseCaseObservable(GetLoginData.SubmitEvent.createObservable()).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSuccess()) {
                view.renderProfile("Zalogowano jako: " + submitUiModel.getResult().getUsername());
            }
        }, Timber::d);
    }

    private void fetchToken() {
        getToken.buildUseCaseObservable(GetAccessToken.SubmitEvent.createObservable()).subscribe(getTokenObserver());
    }
}
