package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.gallery.model.Person;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;

import java.util.List;

interface GridPhotoContract {

    interface View extends BaseView {
        void showPhotos(List<PhotoWithAuthor> photos);

        void openPhotoView(PhotoWithAuthor photo);

        void openUserProfile(Person person);
    }

    interface Presenter extends BasePresenter<View> {
        void refreshPhotos(String userId);

        void photoSelected(PhotoWithAuthor photo);

        void profileSelected(Person person);
    }
}
