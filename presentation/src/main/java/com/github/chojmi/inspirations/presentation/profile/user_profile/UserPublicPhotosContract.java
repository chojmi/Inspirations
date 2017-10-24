package com.github.chojmi.inspirations.presentation.profile.user_profile;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;

import java.util.List;

public interface UserPublicPhotosContract {
    interface View extends BaseView {
        void renderView(List<Photo> photos);
    }

    interface Presenter extends BasePresenter<UserPublicPhotosContract.View> {
        void setUserId(@NonNull String userId);
    }
}
