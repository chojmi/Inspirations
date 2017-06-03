package com.github.chojmi.inspirations.data.source.remote.module;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.data.source.local.LocalPeopleDataSource;
import com.github.chojmi.inspirations.data.source.remote.data_source.RemotePeopleDataSource;
import com.github.chojmi.inspirations.data.source.repository.PeopleRepository;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class PeopleRepositoryModule {
    @Provides
    @Local
    PeopleDataSource provideLocalGalleryDataSource(LocalPeopleDataSource localPeopleDataSource) {
        return localPeopleDataSource;
    }

    @Provides
    @Remote
    PeopleDataSource provideRemoteGalleryDataSource(RemotePeopleDataSource remotePeopleDataSource) {
        return remotePeopleDataSource;
    }

    @Singleton
    @Provides
    PeopleDataSource provideGalleryDataSource(PeopleRepository peopleRepository) {
        return peopleRepository;
    }
}
