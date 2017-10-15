package com.github.chojmi.inspirations.presentation.search;

import android.util.Pair;
import android.widget.ImageView;

import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;

import java.util.List;

import io.reactivex.Observable;

public interface SearchPhotosContract {
    interface View extends BaseView {
        Observable<CharSequence> getSearchObservable();

        Observable<Pair<ImageView, Photo>> getPhotoClicksSubject();

        void renderView(List<Photo> photos);

        void toggleProgressBar(boolean isVisible);
    }

    interface Presenter extends BasePresenter<SearchPhotosContract.View> {
        void search(String text);
    }
}
