package com.github.chojmi.inspirations.presentation.profile.user_profile;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

public interface UserProfileContract {
    interface View extends BaseView {
        void renderProfile(PersonEntity personEntity);
    }

    interface Presenter extends BasePresenter<UserProfileContract.View> {
    }
}
