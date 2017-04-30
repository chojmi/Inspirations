package com.github.chojmi.inspirations.data.source.module;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.data.source.local.LocalPeopleDataSource;
import com.github.chojmi.inspirations.data.source.remote.RemotePeopleDataSource;
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
    PeopleDataSource provideLocalGalleryDataSource(Context context) {
        return new LocalPeopleDataSource(context);
    }

    @Provides
    @Remote
    PeopleDataSource provideRemoteGalleryDataSource(Context context) {
        return new RemotePeopleDataSource(context);
    }

    @Provides
    PeopleDataSource provideGalleryDataSource(PeopleRepository peopleRepository) {
        return peopleRepository;
    }
}
