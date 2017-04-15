package com.github.chojmi.inspirations.data.source;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.local.LocalPeopleDataSource;
import com.github.chojmi.inspirations.data.source.remote.FakeRemotePeopleDataSource;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class PeopleRepositoryModule {
    @Provides
    @Local
    PeopleDataSource providePeopleLocalDataSource(Context context) {
        return new LocalPeopleDataSource(context);
    }

    @Provides
    @Remote
    PeopleDataSource providePeopleRemoteDataSource() {
        return new FakeRemotePeopleDataSource();
    }

    @Provides
    PeopleDataSource providePeopleRepository(PeopleRepository peopleRepository) {
        return peopleRepository;
    }
}
