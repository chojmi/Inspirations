package com.github.chojmi.inspirations.presentation;

import com.github.chojmi.inspirations.data.source.GalleryRepository;
import com.github.chojmi.inspirations.data.source.GalleryRepositoryModule;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;
import com.github.chojmi.inspirations.presentation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GalleryRepositoryModule.class, ApplicationModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    PostExecutionThread getPostExecutionThread();

    Navigator getNavigator();

    GalleryRepository getGalleryRepository();
}
