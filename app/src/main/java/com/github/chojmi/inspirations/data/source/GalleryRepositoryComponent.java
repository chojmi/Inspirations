package com.github.chojmi.inspirations.data.source;

import com.github.chojmi.inspirations.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GalleryRepositoryModule.class, ApplicationModule.class})
public interface GalleryRepositoryComponent {

    GalleryRepository getGalleryRepository();
}