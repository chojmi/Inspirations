package com.github.chojmi.inspirations.presentation.gallery.ui.comments;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

import io.reactivex.Observable;

public interface CommentsContract {
    interface View extends BaseView {
        void toggleProgressBar(boolean isVisible);

        void renderView(PhotoCommentsEntity photoComments);

        void closeView();

        Observable<android.view.View> getBackBtnClicksObservable();
    }

    interface Presenter extends BasePresenter<View> {
    }
}
