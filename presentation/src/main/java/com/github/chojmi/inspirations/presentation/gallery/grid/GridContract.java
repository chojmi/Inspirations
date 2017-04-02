package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.domain.model.Photo;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

import java.util.List;

public interface GridContract {

    interface View extends BaseView, GridAdapter.Listener {
        void showPhotos(List<Photo> photos);

    }

    interface Presenter extends BasePresenter<View> {
        void refreshPhotos(String galleryId);

        void photoSelected(Photo photo);
    }
}
