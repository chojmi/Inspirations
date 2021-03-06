package com.github.chojmi.inspirations.presentation.profile.login;

import com.github.chojmi.inspirations.domain.usecase.auth.GetAccessToken;
import com.github.chojmi.inspirations.domain.usecase.auth.GetAuthorizationUrl;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class LoginWebViewPresenter implements LoginWebViewContract.Presenter {
    private final GetAuthorizationUrl getAuthorizationUrl;
    private final GetAccessToken getToken;
    private CompositeDisposable disposables;
    private LoginWebViewContract.View view;

    public LoginWebViewPresenter(@NonNull GetAuthorizationUrl getAuthorizationUrl, @NonNull GetAccessToken getToken) {
        this.getAuthorizationUrl = checkNotNull(getAuthorizationUrl);
        this.getToken = checkNotNull(getToken);
    }

    @Override
    public void setView(@NonNull LoginWebViewContract.View view) {
        this.disposables = new CompositeDisposable();
        this.view = checkNotNull(view);
        openLoginPage();
    }

    private void openLoginPage() {
        disposables.add(getAuthorizationUrl.process()
                .doOnComplete(() -> view.toggleProgressBar(false))
                .subscribe(submitUiModel -> {
            view.toggleProgressBar(submitUiModel.isInProgress());
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.loadLoginPage(submitUiModel.getResult());
            }
        }, Timber::e));
    }

    @Override
    public void onVerifierTokenObtained(String verifier) {
        fetchAccessToken(verifier);
    }

    private void fetchAccessToken(String verifier) {
        disposables.add(getToken.process(verifier)
                .doOnComplete(() -> view.toggleProgressBar(false))
                .subscribe(submitUiModel -> {
            view.toggleProgressBar(submitUiModel.isInProgress());

            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.closeSuccessfully();
            }
        }, Timber::d));
    }

    @Override
    public void destroyView() {
        this.disposables.dispose();
        this.view = null;
    }
}