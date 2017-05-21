package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;
import com.github.chojmi.inspirations.presentation.model.gallery.PhotoComments;
import com.github.chojmi.inspirations.presentation.model.gallery.PhotoFavs;

public interface GridPhotoAttrsContract {
    interface View extends BaseView {
        void showFavs(int position, PhotoFavs photoFavs);

        void showComments(int position, PhotoComments photoComments);

    }

    interface Presenter extends BasePresenter<GridPhotoAttrsContract.View> {
        void loadFavs(int position, Photo photo);

        void loadComments(int position, Photo photo);
    }
}
