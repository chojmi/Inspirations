package com.github.chojmi.inspirations.data.source.module;

import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.data.source.remote.service.AuthTestService;
import com.github.chojmi.inspirations.data.source.remote.service.FakeAuthTestService;
import com.github.chojmi.inspirations.data.source.remote.service.FakeFavoritesService;
import com.github.chojmi.inspirations.data.source.remote.service.FakeGalleriesService;
import com.github.chojmi.inspirations.data.source.remote.service.FakeOAuthService;
import com.github.chojmi.inspirations.data.source.remote.service.FakePeopleService;
import com.github.chojmi.inspirations.data.source.remote.service.FakePhotosService;
import com.github.chojmi.inspirations.data.source.remote.service.FakeRemoteQueryProducerImpl;
import com.github.chojmi.inspirations.data.source.remote.service.FavoritesService;
import com.github.chojmi.inspirations.data.source.remote.service.GalleriesService;
import com.github.chojmi.inspirations.data.source.remote.service.OAuthService;
import com.github.chojmi.inspirations.data.source.remote.service.PeopleService;
import com.github.chojmi.inspirations.data.source.remote.service.PhotosService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Remote
@Module
public class RestClientModule {
    @Provides
    GalleriesService provideGalleryService() {
        return new FakeGalleriesService();
    }

    @Provides
    PeopleService providePeopleService() {
        return new FakePeopleService();
    }

    @Provides
    PhotosService providePhotosService() {
        return new FakePhotosService();
    }

    @Provides
    AuthTestService provideAuthTestService() {
        return new FakeAuthTestService();
    }

    @Provides
    FavoritesService provideFavoritesService() {
        return new FakeFavoritesService();
    }

    @Singleton
    @Provides
    OAuthService provideOAuthService() {
        return new FakeOAuthService();
    }

    @Provides
    RemoteQueryProducer provideRemoteQueryProducer() {
        return new FakeRemoteQueryProducerImpl();
    }

}
