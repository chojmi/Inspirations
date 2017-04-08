package com.github.chojmi.inspirations.presentation.gallery.grid;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.usecase.DefaultObserver;
import com.github.chojmi.inspirations.domain.usecase.GetGallery;
import com.github.chojmi.inspirations.domain.usecase.GetGallery.Params;
import com.github.chojmi.inspirations.presentation.blueprints.exception.ViewNotFoundException;
import com.github.chojmi.inspirations.presentation.mapper.gallery.PhotoDataMapper;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

import java.util.List;

import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

class GridPresenter implements GridContract.Presenter {
    private final GetGallery getGalleryUseCase;
    private final PhotoDataMapper photoDataMapper;

    private GridContract.View view;

    GridPresenter(@NonNull GetGallery getGalleryUseCase, @NonNull PhotoDataMapper photoDataMapper) {
        this.getGalleryUseCase = checkNotNull(getGalleryUseCase);
        this.photoDataMapper = checkNotNull(photoDataMapper);
    }

    @Override
    public void setView(@NonNull GridContract.View view) {
        this.view = checkNotNull(view);
        refreshPhotos("72157677898551390");
    }

    @Override
    public void refreshPhotos(String galleryId) {
        if (view == null) {
            throw new ViewNotFoundException();
        }
        getGalleryUseCase.execute(getGalleryObserver(), Params.forGallery(galleryId));
    }

    @Override
    public void photoSelected(Photo photo) {
        view.openPhotoView(photo);
    }

    @Override
    public void destroyView() {
        this.getGalleryUseCase.dispose();
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
