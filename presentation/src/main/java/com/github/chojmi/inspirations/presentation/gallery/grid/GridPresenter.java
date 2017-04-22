package com.github.chojmi.inspirations.presentation.gallery.grid;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.usecase.DefaultObserver;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.presentation.blueprints.exception.ViewNotFoundException;
import com.github.chojmi.inspirations.presentation.mapper.gallery.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

import io.reactivex.Observable;
import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

class GridPresenter implements GridContract.Presenter {
    private final GetUserPublicPhotos getUserPublicPhotos;
    private final GetUserInfo getUserInfo;
    private final PhotoDataMapper photoDataMapper;

    private GridContract.View view;

    GridPresenter(@NonNull GetUserPublicPhotos getUserPublicPhotos, @NonNull GetUserInfo getUserInfo, @NonNull PhotoDataMapper photoDataMapper) {
        this.getUserPublicPhotos = checkNotNull(getUserPublicPhotos);
        this.getUserInfo = checkNotNull(getUserInfo);
        this.photoDataMapper = checkNotNull(photoDataMapper);
    }

    @Override
    public void setView(@NonNull GridContract.View view) {
        this.view = checkNotNull(view);
        refreshPhotos("66956608@N06");
    }

    @Override
    public void refreshPhotos(@NonNull String userId) {
        if (view == null) {
            throw new ViewNotFoundException();
        }
        getUserPublicPhotos.execute(getGalleryObserver(), Observable.fromCallable(() -> GetUserPublicPhotos.SubmitEvent.create(checkNotNull(userId))));
    }

    @Override
    public void photoSelected(Photo photo) {
        view.openPhotoView(photo);
    }

    @Override
    public void destroyView() {
        this.getUserPublicPhotos.dispose();
        this.getUserInfo.dispose();
        this.view = null;
    }

    private DefaultObserver<GetUserPublicPhotos.SubmitUiModel> getGalleryObserver() {
        return new DefaultObserver<GetUserPublicPhotos.SubmitUiModel>() {
            @Override
            public void onNext(GetUserPublicPhotos.SubmitUiModel model) {
                if (model.isInProgress()) {
                    return;
                }
                if (model.isSuccess()) {
                    view.showPhotos(photoDataMapper.transform(model.getResult()));
                }
            }

            @Override
            public void onError(Throwable exception) {
                Timber.e(exception);
            }
        };
    }
}
