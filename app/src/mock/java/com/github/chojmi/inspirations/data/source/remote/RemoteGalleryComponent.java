package com.github.chojmi.inspirations.data.source.remote;

import com.github.chojmi.inspirations.data.source.GalleryDataSource;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component
public interface RemoteGalleryComponent {

    void inject(GalleryDataSource galleryDataSource);
}
