package com.github.chojmi.inspirations.presentation.search;

import com.github.chojmi.inspirations.presentation.search.photos.SearchPhotosView;

import dagger.Subcomponent;

@SearchScope
@Subcomponent(modules = SearchModule.class)
public interface SearchComponent {
    void inject(SearchFragment target);

    void inject(SearchPhotosView target);
}
