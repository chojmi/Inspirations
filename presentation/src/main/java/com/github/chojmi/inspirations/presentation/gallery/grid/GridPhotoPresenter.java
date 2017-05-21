package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.domain.usecase.blueprints.DefaultObserver;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserPublicPhotos;
import com.github.chojmi.inspirations.presentation.blueprints.exception.ViewNotFoundException;
import com.github.chojmi.inspirations.presentation.mapper.gallery.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;
import com.github.chojmi.inspirations.presentation.model.gallery.PhotoSubmitUiModel;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

class GridPhotoPresenter implements GridPhotoContract.Presenter {
    private final GetUserPublicPhotos getUserPublicPhotos;
    private final GetUserInfo getUserInfo;
    private final PhotoDataMapper photoDataMapper;
    private final CompositeDisposable disposables;
    private GridPhotoContract.View view;

    GridPhotoPresenter(@NonNull GetUserPublicPhotos getUserPublicPhotos, @NonNull GetUserInfo getUserInfo, @NonNull PhotoDataMapper photoDataMapper) {
        this.getUserPublicPhotos = checkNotNull(getUserPublicPhotos);
        this.getUserInfo = checkNotNull(getUserInfo);
        this.photoDataMapper = checkNotNull(photoDataMapper);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void setView(@NonNull GridPhotoContract.View view) {
        this.view = checkNotNull(view);
        refreshPhotos("66956608@N06");
    }

    @Override
    public void refreshPhotos(@NonNull String userId) {
        if (view == null) {
            throw new ViewNotFoundException();
        }
        disposables.add(getPhotoObservable(userId).subscribe(uiModel -> {
            if (uiModel.isInProgress()) {
                return;
            }
            if (uiModel.isSuccess()) {
                view.showPhotos(uiModel.getPhotos());
            }
        }, Timber::e));
    }

    @Override
    public void photoSelected(Photo photo) {
        view.openPhotoView(photo);
    }

    @Override
    public void destroyView() {
        this.getUserPublicPhotos.dispose();
        this.getUserInfo.dispose();
        this.disposables.dispose();
        this.view = null;
    }

    private Observable<PhotoSubmitUiModel> getPhotoObservable(@NonNull String userId) {
        return Observable.zip(
                Observable.create(e -> getUserPublicPhotos.execute(DefaultObserver.create(e), Observable.fromCallable(() -> GetUserPublicPhotos.SubmitEvent.create(checkNotNull(userId))))),
                Observable.create(e -> getUserInfo.execute(DefaultObserver.create(e), Observable.fromCallable(() -> GetUserInfo.SubmitEvent.create(checkNotNull(userId))))),
                (BiFunction<GetUserPublicPhotos.SubmitUiModel, GetUserInfo.SubmitUiModel, PhotoSubmitUiModel>) (publicPhotosModel, userInfoModel) -> {
                    if (publicPhotosModel.isInProgress() || userInfoModel.isInProgress()) {
                        return PhotoSubmitUiModel.inProgress();
                    }
                    if (publicPhotosModel.getErrorMessage() != null) {
                        return PhotoSubmitUiModel.failure(publicPhotosModel.getErrorMessage());
                    }
                    if (userInfoModel.getErrorMessage() != null) {
                        return PhotoSubmitUiModel.failure(userInfoModel.getErrorMessage());
                    }
                    if (publicPhotosModel.isSuccess() && userInfoModel.isSuccess()) {
                        return PhotoSubmitUiModel.success(photoDataMapper.transform(publicPhotosModel.getResult(), userInfoModel.getResult()));
                    }
                    return PhotoSubmitUiModel.failure(new Throwable());
                });
    }
}
