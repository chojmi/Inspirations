package com.github.chojmi.inspirations.presentation.my_profile.login;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

public interface LoginWebViewContract {
    interface View extends BaseView {
        void loadLoginPage(String url);
    }

    interface Presenter extends BasePresenter<View> {

    }
}