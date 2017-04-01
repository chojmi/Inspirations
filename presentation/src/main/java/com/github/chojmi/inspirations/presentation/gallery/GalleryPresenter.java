package com.github.chojmi.inspirations.presentation.gallery;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.model.Photo;
import com.github.chojmi.inspirations.domain.usecase.DefaultObserver;
import com.github.chojmi.inspirations.domain.usecase.GetGallery;
import com.github.chojmi.inspirations.domain.usecase.GetGallery.Params;
import com.github.chojmi.inspirations.presentation.blueprints.exception.ViewNotFoundException;

import java.util.List;

import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

public class GalleryPresenter implements GalleryContract.Presenter {
    private final GetGallery getGalleryUseCase;

    private GalleryContract.View galleryView;

    public GalleryPresenter(@NonNull GetGallery getGalleryUseCase) {
        this.getGalleryUseCase = checkNotNull(getGalleryUseCase);
    }

    @Override
    public void setView(@NonNull GalleryContract.View view) {
        galleryView = checkNotNull(view);
        refreshPhotos("72157677898551390");
    }

    @Override
    public void refreshPhotos(String galleryId) {
        if (galleryView == null) {
            throw new ViewNotFoundException();
        }
        getGalleryUseCase.execute(new GalleryObserver(), Params.forGallery(galleryId));
    }

    @Override
    public void destroyView() {
        this.getGalleryUseCase.dispose();
        this.galleryView = null;
    }

    private final class GalleryObserver extends DefaultObserver<List<Photo>> {
        @Override
        public void onNext(List<Photo> photos) {
            galleryView.showPhotos(photos);
        }

        @Override
        public void onError(Throwable exception) {
            Timber.e(exception);
        }
    }
}
