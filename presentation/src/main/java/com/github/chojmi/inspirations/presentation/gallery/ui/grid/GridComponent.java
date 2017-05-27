package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import com.github.chojmi.inspirations.presentation.gallery.ui.GalleryScope;

import dagger.Subcomponent;

@GalleryScope
@Subcomponent(modules = GridModule.class)
public interface GridComponent {
    void inject(GridFragment target);
}
