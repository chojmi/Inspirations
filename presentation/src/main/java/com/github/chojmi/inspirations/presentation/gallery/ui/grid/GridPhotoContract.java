package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import android.widget.ImageView;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity;
import com.github.chojmi.inspirations.presentation.blueprints.BasePresenter;
import com.github.chojmi.inspirations.presentation.blueprints.BaseView;
import com.github.chojmi.inspirations.presentation.gallery.model.Person;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;

import java.util.List;

import io.reactivex.Observable;

interface GridPhotoContract {

    interface View extends BaseView {
        void showPhotos(List<PhotoWithAuthor> photos);

        void openPhotoView(int position, ImageView imageView);

        void openUserProfile(Person person);

        void goToFavs(Photo photo);

        void showComments(PhotoWithAuthor photo);

        void toggleProgressBar(boolean isVisible);

        void refreshFavSelection(int position, Observable<Boolean> isFav);
    }

    interface Presenter extends BasePresenter<View> {
        void refreshPhotos(String userId);

        void photoSelected(int position, ImageView imageView);

        void profileSelected(Person person);

        void favsSelected(PhotoWithAuthor photo);

        void commentsSelected(PhotoWithAuthor photo);

        void favIconSelected(int position, PhotoInfoEntity photo);
    }
}
