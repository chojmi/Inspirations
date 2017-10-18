package com.github.chojmi.inspirations.presentation.common;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public interface FavToggler {
    Observable<Boolean> toggleFav(boolean isFav, @NonNull String photoId);

    void onDestroy();
}
