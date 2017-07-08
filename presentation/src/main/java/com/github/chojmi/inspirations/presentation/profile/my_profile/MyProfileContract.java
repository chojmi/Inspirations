package com.github.chojmi.inspirations.presentation.profile.my_profile;

import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

public interface MyProfileContract {
    interface View extends BaseView {
        void showProfile(UserEntity userEntity);
    }

    interface Presenter extends BasePresenter<View> {
        void loginSuccessful();
    }
}