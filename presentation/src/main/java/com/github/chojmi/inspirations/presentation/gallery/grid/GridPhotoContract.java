package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

import java.util.List;

interface GridPhotoContract {

    interface View extends BaseView {
        void showPhotos(List<Photo> photos);

        void openPhotoView(Photo photo);
    }

    interface Presenter extends BasePresenter<View> {
        void refreshPhotos(String userId);

        void photoSelected(Photo photo);
    }
}
