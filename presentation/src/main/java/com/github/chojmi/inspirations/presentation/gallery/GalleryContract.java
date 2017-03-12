package com.github.chojmi.inspirations.presentation.gallery;

import com.github.chojmi.inspirations.data.model.Photo;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

import java.util.List;

public interface GalleryContract {

    interface View extends BaseView<UserActionsListener> {
        void showPhotos(List<Photo> photos);

    }

    interface UserActionsListener extends BasePresenter {
        void refreshPhotos(String galleryId);
    }
}
