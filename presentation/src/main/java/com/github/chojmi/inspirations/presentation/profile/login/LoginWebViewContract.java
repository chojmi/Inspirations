package com.github.chojmi.inspirations.presentation.profile.login;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

public interface LoginWebViewContract {
    interface View extends BaseView {
        void loadLoginPage(String url);

        void toggleProgressBar(boolean isVisible);

        void closeSuccessfully();
    }

    interface Presenter extends BasePresenter<View> {
        void onVerifierTokenObtained(String verifier);
    }
}