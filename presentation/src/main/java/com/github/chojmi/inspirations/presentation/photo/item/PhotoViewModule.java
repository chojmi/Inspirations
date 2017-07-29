package com.github.chojmi.inspirations.presentation.photo.item;

import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.photo.PhotoScope;

import dagger.Module;
import dagger.Provides;

@PhotoScope
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
