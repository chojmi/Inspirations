package com.github.chojmi.inspirations.presentation.photo.item;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;

interface PhotoViewContract {
    interface View extends BaseView {
        void showPhoto(Photo photo);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
