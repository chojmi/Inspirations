package com.github.chojmi.inspirations.presentation.gallery.ui.fav_list;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

public interface FavListContract {
    interface View extends BaseView {
        void toggleProgressBar(boolean isVisible);

        void renderView(PhotoFavsEntity photoFavs);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
