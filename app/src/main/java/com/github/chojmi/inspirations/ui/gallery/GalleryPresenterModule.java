package com.github.chojmi.inspirations.ui.gallery;

import dagger.Module;
import dagger.Provides;

@Module
class GalleryPresenterModule {
    private final GalleryContract.View view;

    public GalleryPresenterModule(GalleryContract.View view) {
        this.view = view;
    }

    @Provides
    public GalleryContract.View getView() {
        return view;
    }
}
