package com.github.chojmi.inspirations.presentation.gallery.ui.favs;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

import io.reactivex.Observable;

public interface FavsContract {
    interface View extends BaseView {
        void toggleProgressBar(boolean isVisible);

        void renderView(PhotoFavsEntity photoFavs);

        Observable<PersonEntity> getOnPersonSelectedObservable();

        void showPerson(PersonEntity personEntity);

        Observable<android.view.View> getBackBtnClicksObservable();

        void closeView();

        void addItems(PhotoFavsEntity result);
    }

    interface Presenter extends BasePresenter<View> {

        void loadPage(int page);
    }
}
