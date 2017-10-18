package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeListEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;

public interface GridPhotoAttrsContract {
    interface View extends BaseView {
        void showFavs(int position, PhotoFavs photoFavs);

        void showPhotoInfo(int position, PhotoInfoEntity photoInfo);

        void showPhotoSizes(int position, PhotoSizeListEntity sizeList);
    }

    interface Presenter extends BasePresenter<GridPhotoAttrsContract.View> {
        void loadFavs(int position, PhotoWithAuthor photo);

        void loadPhotoInfo(int position, PhotoWithAuthor photo);

        void loadPhotoSizes(int position, PhotoWithAuthor photo);
    }
}
