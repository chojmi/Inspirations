package com.github.chojmi.inspirations.presentation.profile.my_profile;

import com.github.chojmi.inspirations.domain.entity.auth.TokenEntity;
import com.github.chojmi.inspirations.domain.usecase.auth.GetToken;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

public class MyProfilePresenter implements MyProfileContract.Presenter {
    private final GetToken getToken;
    private final CompositeDisposable disposables;
    private MyProfileContract.View view;

    public MyProfilePresenter(@NonNull GetToken getToken) {
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

    private Observer<GetToken.SubmitUiModel> getTokenObserver() {
        return new DisposableObserver<GetToken.SubmitUiModel>() {
            TokenEntity tokenEntity = null;

            @Override
            public void onNext(GetToken.SubmitUiModel submitUiModel) {
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

    private void fetchProfile(TokenEntity tokenEntity) {
        //TODO: Fetch all profile data
        view.renderProfile("Zalogowano");
    }

    private void fetchToken() {
        getToken.buildUseCaseObservable(GetToken.SubmitEvent.createCacheObservable()).subscribe(getTokenObserver());
    }
}
