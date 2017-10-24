package com.github.chojmi.inspirations.presentation.search;

import android.content.Context;

import com.github.chojmi.inspirations.domain.usecase.photos.GetSearchPhoto;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.common.mapper.PhotoDataMapper;

import dagger.Module;
import dagger.Provides;

@SearchScope
@Module
public class SearchModule {

    @Provides
    SearchPhotosContract.Presenter provideSearchPhotosPresenter(Context context, GetSearchPhoto getSearchPhoto, PhotoDataMapper photoDataMapper) {
        return new SearchPhotosPresenter(getSearchPhoto, photoDataMapper, context.getString(R.string.initial_search_query));
    }
}
