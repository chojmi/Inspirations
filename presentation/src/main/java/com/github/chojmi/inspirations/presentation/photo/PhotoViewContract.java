package com.github.chojmi.inspirations.presentation.photo;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;

interface PhotoViewContract {
    interface View extends BaseView {
        void showPhoto(Photo photo);

        void showFavs(PhotoFavs photoFavs);

        void showPhotoInfo(PhotoInfoEntity photoInfo);

        void showUserData(PhotoWithAuthor photoWithAuthor);

        void goToFavs(Photo photo);

        void showComments(Photo photo);
    }

    interface Presenter extends BasePresenter<View> {
        void favIconSelected(boolean isFav);

        void favsSelected();

        void commentsSelected();
    }
}
