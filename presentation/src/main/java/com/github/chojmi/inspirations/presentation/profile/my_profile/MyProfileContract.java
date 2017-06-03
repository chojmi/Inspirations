package com.github.chojmi.inspirations.presentation.profile.my_profile;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

public interface MyProfileContract {
    interface View extends BaseView {
        void isLoggedIn(boolean isLoggedIn);

        void renderProfile(String name);
    }

    interface Presenter extends BasePresenter<View> {

        void loginSuccessful();
    }
}