package com.github.chojmi.inspirations.ui.gallery;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.data.source.GalleryRepository;

import javax.inject.Inject;

import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;

public class GalleryPresenter implements GalleryContract.UserActionsListener {
    private final GalleryRepository galleryRepository;
    private final GalleryContract.View galleryView;

    @Inject
    public GalleryPresenter(@NonNull GalleryRepository galleryRepository, @NonNull GalleryContract.View galleryView) {
        this.galleryRepository = checkNotNull(galleryRepository);
        this.galleryView = checkNotNull(galleryView);
    }

    @Inject
    void setupListeners() {
        galleryView.setPresenter(this);
    }

    @Override
    public void start() {
        refreshPhotos("72157677898551390");
    }

    @Override
    public void refreshPhotos(String galleryId) {
        galleryRepository.loadGallery(galleryId).subscribe(gallery -> galleryView.showPhotos(gallery.getPhoto()), Timber::e);
    }
}
