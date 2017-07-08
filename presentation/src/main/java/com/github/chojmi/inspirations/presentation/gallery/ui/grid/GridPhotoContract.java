package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.gallery.model.Person;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;

import java.util.List;

interface GridPhotoContract {

    interface View extends BaseView {
        void showPhotos(List<Photo> photos);

        void openPhotoView(Photo photo);

        void openUserProfile(Person person);
    }

    interface Presenter extends BasePresenter<View> {
        void refreshPhotos(String userId);

        void photoSelected(Photo photo);

        void profileSelected(Person person);
    }
}
