package com.github.chojmi.inspirations.presentation.search;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;

import java.util.List;

public interface SearchPhotosContract {
    interface View extends BaseView {
        void renderView(List<Photo> photos);

        void toggleProgressBar(boolean isVisible);
    }

    interface Presenter extends BasePresenter<SearchPhotosContract.View> {
        void search(String text);
    }
}
