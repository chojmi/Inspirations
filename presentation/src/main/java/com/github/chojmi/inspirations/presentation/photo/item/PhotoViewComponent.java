package com.github.chojmi.inspirations.presentation.photo.item;

import com.github.chojmi.inspirations.presentation.gallery.ui.GalleryScope;

import dagger.Subcomponent;

@GalleryScope
@Subcomponent(modules = PhotoViewModule.class)
public interface PhotoViewComponent {
    void inject(PhotoViewActivity target);
}
