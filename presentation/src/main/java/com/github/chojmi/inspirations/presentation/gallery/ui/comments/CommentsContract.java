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

        void clearComment();

        Observable<String> getUserClicksObservable();

        void showUser(String userId);

        void showLoginInfo();
    }

    interface Presenter extends BasePresenter<View> {
        void addComment(String commentText);
    }
}
