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
    private String currentFrob = "";

    public LoginWebViewPresenter(@NonNull GetAuthorizationUrl getAuthorizationUrl, @NonNull GetAccessToken getToken) {
        this.getAuthorizationUrl = checkNotNull(getAuthorizationUrl);
        this.getToken = checkNotNull(getToken);
    }

    @Override
    public void setView(@NonNull LoginWebViewContract.View view) {
        this.view = checkNotNull(view);
        this.disposables = new CompositeDisposable();
        openLoginPage();
    }

    private void openLoginPage() {
        disposables.add(getAuthorizationUrl.process().subscribe(submitUiModel -> {
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
        disposables.add(getToken.process(verifier).subscribe(submitUiModel -> {
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

    @Override
    public void pageLoaded(String url) {
        if (isLogged(url)) {
            fetchAccessToken(currentFrob);
        }
    }

    private boolean isLogged(String url) {
        return "https://www.flickr.com/services/auth/".equals(url);
    }

    @Override
    public boolean isPermittedUrl(String url) {
        return true;
    }
}