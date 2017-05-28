package com.github.chojmi.inspirations.presentation;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.remote.data_source.RemoteAuthDataSource;
import com.github.chojmi.inspirations.data.source.remote.module.AuthRepositoryModule;
import com.github.chojmi.inspirations.data.source.remote.module.GalleriesRepositoryModule;
import com.github.chojmi.inspirations.data.source.remote.module.PeopleRepositoryModule;
import com.github.chojmi.inspirations.data.source.remote.module.PhotosRepositoryModule;
import com.github.chojmi.inspirations.data.source.remote.module.RestClientModule;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.GridComponent;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.GridModule;
import com.github.chojmi.inspirations.presentation.gallery.ui.photo.PhotoViewComponent;
import com.github.chojmi.inspirations.presentation.gallery.ui.photo.PhotoViewModule;
import com.github.chojmi.inspirations.presentation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GalleriesRepositoryModule.class, PeopleRepositoryModule.class, PhotosRepositoryModule.class, AuthRepositoryModule.class, ApplicationModule.class, RestClientModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity target);

    GridComponent plus(GridModule gridModule);

    PhotoViewComponent plus(PhotoViewModule photoViewModule);

    Context getContext();

    ThreadExecutor getThreadExecutor();

    PostExecutionThread getPostExecutionThread();

    Navigator getNavigator();

    GalleriesDataSource getGalleriesRepository();

    PeopleDataSource getPeopleDataSource();

    PhotosDataSource getPhotosDataSource();

    RemoteAuthDataSource getRemoteAuthDataSource();
}
