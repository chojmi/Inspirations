package com.github.chojmi.inspirations.presentation;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.GalleryRepositoryModule;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;
import com.github.chojmi.inspirations.presentation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GalleryRepositoryModule.class, ApplicationModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    Context getContext();

    ThreadExecutor getThreadExecutor();

    PostExecutionThread getPostExecutionThread();

    Navigator getNavigator();

    GalleryDataSource getGalleryRepository();
}
