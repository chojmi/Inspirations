package com.github.chojmi.inspirations.presentation.gallery.photo;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

import static dagger.internal.Preconditions.checkNotNull;

class PhotoViewPresenter implements PhotoViewContract.Presenter {
    private PhotoViewContract.View view;
    private Photo photo;

    PhotoViewPresenter(Photo photo) {
        this.photo = photo;
    }

    @Override
    public void setView(@NonNull PhotoViewContract.View view) {
        this.view = checkNotNull(view);
        view.showPhoto(photo);
    }

    @Override
    public void destroyView() {
        this.view = null;
    }
}
