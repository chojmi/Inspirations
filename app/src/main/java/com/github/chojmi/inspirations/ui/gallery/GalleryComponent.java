package com.github.chojmi.inspirations.ui.gallery;

import com.github.chojmi.inspirations.data.source.GalleryRepositoryComponent;
import com.github.chojmi.inspirations.ui.blueprints.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = GalleryRepositoryComponent.class, modules = GalleryPresenterModule.class)
interface GalleryComponent {

    void inject(GalleryActivity activity);

}
