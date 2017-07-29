package com.github.chojmi.inspirations.presentation.search.photos;

import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

public interface SearchPhotosContract {
    interface View extends BaseView {
        void renderView(GalleryEntity gallery);
    }

    interface Presenter extends BasePresenter<SearchPhotosContract.View> {
        void search(String text);
    }
}
