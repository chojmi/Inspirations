package com.github.chojmi.inspirations.data.source.remote;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RestClientModule.class)
public interface RemoteComponent {

    void inject(RemoteGalleriesDataSource remoteGalleryDataSource);

    void inject(RemotePeopleDataSource remotePeopleDataSource);

    void inject(RemotePhotosDataSource remotePhotosDataSource);
}
