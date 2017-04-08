package com.github.chojmi.inspirations.presentation.gallery;

import com.github.chojmi.inspirations.presentation.gallery.grid.GridFragment;
import com.github.chojmi.inspirations.presentation.gallery.grid.GridModule;
import com.github.chojmi.inspirations.presentation.gallery.photo.PhotoViewActivity;
import com.github.chojmi.inspirations.presentation.gallery.photo.PhotoViewModule;

import dagger.Subcomponent;

@GalleryScope
@Subcomponent(modules = {GridModule.class, PhotoViewModule.class})
public interface GalleryComponent {
    void inject(GridFragment target);

    void inject(PhotoViewActivity target);
}
