package com.github.chojmi.inspirations.ui.gallery;

import com.github.chojmi.inspirations.data.model.Photo;
import com.github.chojmi.inspirations.ui.blueprints.BasePresenter;
import com.github.chojmi.inspirations.ui.blueprints.BaseView;

import java.util.List;

public interface GalleryContract {

    interface View extends BaseView<UserActionsListener> {
        void showPhotos(List<Photo> photos);

    }

    interface UserActionsListener extends BasePresenter {
        void refreshPhotos(String galleryId);
    }
}
