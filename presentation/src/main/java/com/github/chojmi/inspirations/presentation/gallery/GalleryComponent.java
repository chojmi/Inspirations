package com.github.chojmi.inspirations.presentation.gallery.grid;

import com.github.chojmi.inspirations.presentation.ApplicationComponent;
import com.github.chojmi.inspirations.presentation.blueprints.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = GridPresenterModule.class)
interface GalleryComponent {

    void inject(GridFragment fragment);
}
