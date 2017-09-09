package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeListEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoComments;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;

public interface GridPhotoAttrsContract {
    interface View extends BaseView {
        void showFavs(int position, PhotoFavs photoFavs);

        void showComments(int position, PhotoComments photoComments);

        void showPhotoSizes(int position, PhotoSizeListEntity sizeList);
    }

    interface Presenter extends BasePresenter<GridPhotoAttrsContract.View> {
        void loadFavs(int position, PhotoWithAuthor photo);

        void loadComments(int position, PhotoWithAuthor photo);

        void loadPhotoSizes(int position, PhotoWithAuthor photo);
    }
}
