package com.github.chojmi.inspirations.presentation;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.module.AuthRepositoryModule;
import com.github.chojmi.inspirations.data.source.module.AuthTestRepositoryModule;
import com.github.chojmi.inspirations.data.source.module.GalleriesRepositoryModule;
import com.github.chojmi.inspirations.data.source.module.PeopleRepositoryModule;
import com.github.chojmi.inspirations.data.source.module.PhotosRepositoryModule;
import com.github.chojmi.inspirations.data.source.module.RestClientModule;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.chojmi.inspirations.domain.repository.AuthTestDataSource;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.GridComponent;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.GridModule;
import com.github.chojmi.inspirations.presentation.gallery.ui.photo.PhotoViewComponent;
import com.github.chojmi.inspirations.presentation.gallery.ui.photo.PhotoViewModule;
import com.github.chojmi.inspirations.presentation.main.Navigator;
import com.github.chojmi.inspirations.presentation.profile.login.LoginWebViewComponent;
import com.github.chojmi.inspirations.presentation.profile.login.LoginWebViewModule;
import com.github.chojmi.inspirations.presentation.profile.my_profile.MyProfileComponent;
import com.github.chojmi.inspirations.presentation.profile.my_profile.MyProfileModule;
import com.github.chojmi.inspirations.presentation.profile.user_profile.UserProfileComponent;
import com.github.chojmi.inspirations.presentation.profile.user_profile.UserProfileModule;
import com.github.chojmi.inspirations.presentation.search.SearchComponent;
import com.github.chojmi.inspirations.presentation.search.SearchModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GalleriesRepositoryModule.class, PeopleRepositoryModule.class,
        PhotosRepositoryModule.class, AuthRepositoryModule.class, AuthTestRepositoryModule.class,
        ApplicationModule.class, RestClientModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity target);

    GridComponent plus(GridModule gridModule);

    PhotoViewComponent plus(PhotoViewModule photoViewModule);

    LoginWebViewComponent plus(LoginWebViewModule loginWebViewModule);

    MyProfileComponent plus(MyProfileModule myProfileModule);

    UserProfileComponent plus(UserProfileModule userProfileModule);

    SearchComponent plus(SearchModule searchModule);

    Context getContext();

    ThreadExecutor getThreadExecutor();

    PostExecutionThread getPostExecutionThread();

    Navigator getNavigator();

    GalleriesDataSource getGalleriesRepository();

    PeopleDataSource getPeopleDataSource();

    PhotosDataSource getPhotosDataSource();

    AuthDataSource getAuthDataSource();

    AuthTestDataSource getAuthTestDataSource();
}
