package com.github.chojmi.inspirations.presentation.photo;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoComments;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;

interface PhotoViewContract {
    interface View extends BaseView {
        void showPhoto(Photo photo);

        void showFavs(PhotoFavs photoFavs);

        void showComments(PhotoComments photoComments);

        void showUserData(PhotoWithAuthor photoWithAuthor);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
