package com.github.chojmi.inspirations.presentation.gallery;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.model.Photo;
import com.github.chojmi.inspirations.domain.usecase.DefaultObserver;
import com.github.chojmi.inspirations.domain.usecase.GetGallery;
import com.github.chojmi.inspirations.domain.usecase.GetGallery.Params;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

public class GalleryPresenter implements GalleryContract.Presenter {
    private final GetGallery getGalleryUseCase;

    private GalleryContract.View galleryView;

    @Inject
    public GalleryPresenter(@NonNull GalleryContract.View galleryView, @NonNull GetGallery getGalleryUseCase) {
        this.galleryView = checkNotNull(galleryView);
        this.getGalleryUseCase = checkNotNull(getGalleryUseCase);
    }

    @Inject
    void setupListeners() {
        galleryView.setPresenter(this);
    }

    @Override
    public void refreshPhotos(String galleryId) {
        getGalleryUseCase.execute(new GalleryObserver(), Params.forGallery(galleryId));
    }

    @Override
    public void resume() {
        refreshPhotos("72157677898551390");
    }

    @Override
    public void destroy() {
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
