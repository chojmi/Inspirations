package com.github.chojmi.inspirations.presentation.gallery;

import com.github.chojmi.inspirations.presentation.ApplicationComponent;
import com.github.chojmi.inspirations.presentation.blueprints.ActivityScope;
import com.github.chojmi.inspirations.presentation.gallery.grid.GridFragment;
import com.github.chojmi.inspirations.presentation.gallery.grid.GridPresenterModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = GridPresenterModule.class)
public interface GalleryComponent {

    void inject(GridFragment fragment);
}
