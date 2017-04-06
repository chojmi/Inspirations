package com.github.chojmi.inspirations.presentation.gallery.grid;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.model.Photo;
import com.github.chojmi.inspirations.domain.usecase.DefaultObserver;
import com.github.chojmi.inspirations.domain.usecase.GetGallery;
import com.github.chojmi.inspirations.domain.usecase.GetGallery.Params;
import com.github.chojmi.inspirations.presentation.blueprints.exception.ViewNotFoundException;

import java.util.List;

import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

class GridPresenter implements GridContract.Presenter {
    private final GetGallery getGalleryUseCase;

    private GridContract.View galleryView;

    GridPresenter(@NonNull GetGallery getGalleryUseCase) {
        this.getGalleryUseCase = checkNotNull(getGalleryUseCase);
    }

    @Override
    public void setView(@NonNull GridContract.View view) {
        galleryView = checkNotNull(view);
        refreshPhotos("72157677898551390");
    }

    @Override
    public void refreshPhotos(String galleryId) {
        if (galleryView == null) {
            throw new ViewNotFoundException();
        }
        getGalleryUseCase.execute(getGalleryObserver(), Params.forGallery(galleryId));
    }

    @Override
    public void photoSelected(Photo photo) {
        galleryView.openPhotoView(photo);
    }

    @Override
    public void destroyView() {
        this.getGalleryUseCase.dispose();
        this.galleryView = null;
    }

    private DefaultObserver<List<Photo>> getGalleryObserver() {
        return new DefaultObserver<List<Photo>>() {
            @Override
            public void onNext(List<Photo> photos) {
                galleryView.showPhotos(photos);
            }

            @Override
            public void onError(Throwable exception) {
                Timber.e(exception);
            }
        };
    }
}
