package com.github.chojmi.inspirations.presentation;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.GalleryRepositoryModule;
import com.github.chojmi.inspirations.data.source.PeopleRepositoryModule;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;
import com.github.chojmi.inspirations.presentation.gallery.grid.GridComponent;
import com.github.chojmi.inspirations.presentation.gallery.grid.GridModule;
import com.github.chojmi.inspirations.presentation.gallery.photo.PhotoViewComponent;
import com.github.chojmi.inspirations.presentation.gallery.photo.PhotoViewModule;
import com.github.chojmi.inspirations.presentation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GalleryRepositoryModule.class, PeopleRepositoryModule.class, ApplicationModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity target);

    GridComponent plus(GridModule gridModule);

    PhotoViewComponent plus(PhotoViewModule photoViewModule);

    Context getContext();

    ThreadExecutor getThreadExecutor();

    PostExecutionThread getPostExecutionThread();

    Navigator getNavigator();

    GalleryDataSource getGalleryRepository();

    PeopleDataSource getPeopleDataSource();
}
