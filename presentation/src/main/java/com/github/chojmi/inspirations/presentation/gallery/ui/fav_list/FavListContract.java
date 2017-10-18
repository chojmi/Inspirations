package com.github.chojmi.inspirations.presentation.gallery.ui.fav_list;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;

import io.reactivex.Observable;

public interface FavListContract {
    interface View extends BaseView {
        void toggleProgressBar(boolean isVisible);

        void renderView(PhotoFavsEntity photoFavs);

        Observable<PersonEntity> getOnPersonSelectedObservable();

        void showPerson(PersonEntity personEntity);

        Observable<android.view.View> getBackBtnClicksObservable();

        void closeView();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
