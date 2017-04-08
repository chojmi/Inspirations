package com.github.chojmi.inspirations.presentation.gallery.photo;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

interface PhotoViewContract {
    interface View extends BaseView {
        void showPhoto(Photo photo);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
