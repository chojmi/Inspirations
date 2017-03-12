package com.github.chojmi.inspirations.presentation.gallery;

import com.github.chojmi.inspirations.presentation.ApplicationComponent;
import com.github.chojmi.inspirations.presentation.blueprints.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = GalleryPresenterModule.class)
interface GalleryComponent {

    void inject(GalleryActivity activity);
}
