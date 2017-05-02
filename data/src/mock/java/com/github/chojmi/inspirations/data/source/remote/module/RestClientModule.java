package com.github.chojmi.inspirations.data.source.remote.module;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.data.source.remote.service.FakeGalleriesService;
import com.github.chojmi.inspirations.data.source.remote.service.FakePeopleService;
import com.github.chojmi.inspirations.data.source.remote.service.FakePhotosService;
import com.github.chojmi.inspirations.data.source.remote.service.FakeRemoteQueryProducerImpl;
import com.github.chojmi.inspirations.data.source.remote.service.GalleriesService;
import com.github.chojmi.inspirations.data.source.remote.service.PeopleService;
import com.github.chojmi.inspirations.data.source.remote.service.PhotosService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;

import dagger.Module;
import dagger.Provides;

@Remote
@Module
public class RestClientModule {
    public RestClientModule(Context context) {
    }

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
    RemoteQueryProducer provideRemoteQueryProducer() {
        return new FakeRemoteQueryProducerImpl();
    }

}
