package com.github.chojmi.inspirations.presentation.gallery.grid;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.entity.gallery.PhotoEntity;
import com.github.chojmi.inspirations.domain.usecase.DefaultObserver;
import com.github.chojmi.inspirations.domain.usecase.gallery.GetUserPublicPhotos;
import com.github.chojmi.inspirations.domain.usecase.people.GetUserInfo;
import com.github.chojmi.inspirations.presentation.blueprints.exception.ViewNotFoundException;
import com.github.chojmi.inspirations.presentation.mapper.gallery.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

import java.util.List;

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
    public void refreshPhotos(String userId) {
        if (view == null) {
            throw new ViewNotFoundException();
        }
        getUserPublicPhotos.execute(getGalleryObserver(), GetUserPublicPhotos.Params.forUserId(userId));
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

    private DefaultObserver<List<PhotoEntity>> getGalleryObserver() {
        return new DefaultObserver<List<PhotoEntity>>() {
            @Override
            public void onNext(List<PhotoEntity> photos) {
                view.showPhotos(photoDataMapper.transform(photos));
            }

            @Override
            public void onError(Throwable exception) {
                Timber.e(exception);
            }
        };
    }
}
