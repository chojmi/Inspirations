package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.presentation.gallery.GalleryScope;

import dagger.Subcomponent;

@GalleryScope
@Subcomponent(modules = GridModule.class)
public interface GridComponent {
    void inject(GridFragment target);
}
