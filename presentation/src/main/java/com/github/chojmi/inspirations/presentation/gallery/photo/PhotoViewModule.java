package com.github.chojmi.inspirations.presentation.gallery.photo;

import com.github.chojmi.inspirations.presentation.gallery.GalleryScope;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

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
