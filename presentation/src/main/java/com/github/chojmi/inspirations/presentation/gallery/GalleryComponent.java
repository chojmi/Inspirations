package com.github.chojmi.inspirations.presentation.gallery;

import com.github.chojmi.inspirations.presentation.gallery.grid.GalleryModule;
import com.github.chojmi.inspirations.presentation.gallery.grid.GridFragment;

import dagger.Subcomponent;

@GalleryScope
@Subcomponent(modules = GalleryModule.class)
public interface GalleryComponent {
    void inject(GridFragment fragment);
}
