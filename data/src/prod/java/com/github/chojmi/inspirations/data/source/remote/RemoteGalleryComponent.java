package com.github.chojmi.inspirations.data.source.remote;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RestClientModule.class)
public interface RemoteGalleryComponent {

    void inject(RemoteGalleryDataSource remoteGalleryDataSource);
}
