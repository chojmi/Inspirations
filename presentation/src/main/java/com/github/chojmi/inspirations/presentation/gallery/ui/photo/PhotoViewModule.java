package com.github.chojmi.inspirations.presentation.gallery.ui.photo;

import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.ui.GalleryScope;

import dagger.Module;
import dagger.Provides;

@GalleryScope
@Module
public class PhotoViewModule {
    private final Photo photo;

    public PhotoViewModule(Photo photo) {
        this.photo = photo;
    }

    @Provides
    PhotoViewContract.Presenter providePhotoViewPresenter() {
        return new PhotoViewPresenter(photo);
    }
}
