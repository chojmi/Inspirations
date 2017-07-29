package com.github.chojmi.inspirations.presentation.profile.user_profile.albums;

import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

public interface UserAlbumsContract {
    interface View extends BaseView {
        void renderView(GalleryEntity gallery);
    }

    interface Presenter extends BasePresenter<UserAlbumsContract.View> {
    }
}
