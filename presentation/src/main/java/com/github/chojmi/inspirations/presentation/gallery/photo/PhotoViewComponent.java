package com.github.chojmi.inspirations.presentation.gallery.photo;

import com.github.chojmi.inspirations.presentation.gallery.GalleryScope;

import dagger.Subcomponent;

@GalleryScope
@Subcomponent(modules = PhotoViewModule.class)
public interface PhotoViewComponent {
    void inject(PhotoViewActivity target);
}
