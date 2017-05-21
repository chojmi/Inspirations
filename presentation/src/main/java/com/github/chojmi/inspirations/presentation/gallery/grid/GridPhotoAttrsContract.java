package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;
import com.github.chojmi.inspirations.presentation.model.gallery.PhotoFavs;

public interface GridPhotoAttrsContract {
    interface View extends BaseView {
        void showFavs(int position, PhotoFavs photoFavs);
    }

    interface Presenter extends BasePresenter<GridPhotoAttrsContract.View> {
        void loadFavs(int position, Photo photo);
    }
}
