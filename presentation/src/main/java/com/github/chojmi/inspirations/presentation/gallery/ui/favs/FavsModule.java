package com.github.chojmi.inspirations.presentation.gallery.ui.favs;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;
import com.github.chojmi.inspirations.presentation.gallery.ui.GalleryScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@GalleryScope
@Module
public class FavsModule {

    @Provides
    FavsContract.Presenter provideGridPhotoPresenter(FavsActivity favsActivity, GetPhotoFavs getPhotoFavs) {
        return new FavsPresenter(favsActivity.getArgPhotoId(), getPhotoFavs);
    }

    @Provides
    @Named("fav_list_adapter")
    BaseRecyclerViewAdapter<?, PersonEntity> provideFavListAdapter() {
        return new FavsAdapter();
    }
}
