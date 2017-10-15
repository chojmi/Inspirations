package com.github.chojmi.inspirations.presentation.main;

import com.github.chojmi.inspirations.presentation.gallery.ui.grid.GridFragment;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.GridModule;
import com.github.chojmi.inspirations.presentation.profile.my_profile.MyProfileFragment;
import com.github.chojmi.inspirations.presentation.profile.my_profile.MyProfileModule;
import com.github.chojmi.inspirations.presentation.search.SearchFragment;
import com.github.chojmi.inspirations.presentation.search.SearchModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityFragmentProvider {
    @ContributesAndroidInjector(modules = GridModule.class)
    abstract GridFragment provideGridFragment();

    @ContributesAndroidInjector(modules = SearchModule.class)
    abstract SearchFragment provideSearchFragment();

    @ContributesAndroidInjector(modules = MyProfileModule.class)
    abstract MyProfileFragment provideMyProfileFragment();

}
