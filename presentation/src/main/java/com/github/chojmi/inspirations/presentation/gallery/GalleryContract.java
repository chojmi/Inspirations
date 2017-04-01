package com.github.chojmi.inspirations.presentation.gallery;

import com.github.chojmi.inspirations.domain.model.Photo;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

import java.util.List;

public interface GalleryContract {

    interface View extends BaseView {
        void showPhotos(List<Photo> photos);

    }

    interface Presenter extends BasePresenter<View> {
        void refreshPhotos(String galleryId);
    }
}
