package com.github.chojmi.inspirations.presentation.di;

import android.app.Application;

import com.github.chojmi.inspirations.data.source.module.AuthRepositoryModule;
import com.github.chojmi.inspirations.data.source.module.AuthTestRepositoryModule;
import com.github.chojmi.inspirations.data.source.module.FavoritesRepositoryModule;
import com.github.chojmi.inspirations.data.source.module.GalleriesRepositoryModule;
import com.github.chojmi.inspirations.data.source.module.PeopleRepositoryModule;
import com.github.chojmi.inspirations.data.source.module.PhotosRepositoryModule;
import com.github.chojmi.inspirations.data.source.module.RestClientModule;
import com.github.chojmi.inspirations.presentation.InspirationsApp;
import com.github.chojmi.inspirations.presentation.main.Navigator;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,
        GalleriesRepositoryModule.class,
        PeopleRepositoryModule.class,
        PhotosRepositoryModule.class,
        AuthRepositoryModule.class,
        AuthTestRepositoryModule.class,
        FavoritesRepositoryModule.class,
        RestClientModule.class})
public interface AppComponent {

    void inject(InspirationsApp app);

    Navigator getNavigator();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
