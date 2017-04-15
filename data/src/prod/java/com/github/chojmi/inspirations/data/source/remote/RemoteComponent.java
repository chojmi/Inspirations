package com.github.chojmi.inspirations.data.source.remote;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RestClientModule.class)
public interface RemoteComponent {

    void inject(RemoteGalleryDataSource remoteGalleryDataSource);

    void inject(RemotePeopleDataSource remotePeopleDataSource);
}
