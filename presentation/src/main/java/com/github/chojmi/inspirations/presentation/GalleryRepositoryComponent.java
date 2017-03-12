package com.github.chojmi.inspirations.presentation;

import com.github.chojmi.inspirations.data.source.GalleryRepository;
import com.github.chojmi.inspirations.data.source.GalleryRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GalleryRepositoryModule.class, ApplicationModule.class})
public interface GalleryRepositoryComponent {

    GalleryRepository getGalleryRepository();
}