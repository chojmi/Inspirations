package com.github.chojmi.inspirations.presentation.profile.user_profile.public_photos;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

import java.util.List;

public interface UserPublicPhotosContract {
    interface View extends BaseView {
        void renderView(List<PhotoEntity> photos);
    }

    interface Presenter extends BasePresenter<UserPublicPhotosContract.View> {
    }
}
