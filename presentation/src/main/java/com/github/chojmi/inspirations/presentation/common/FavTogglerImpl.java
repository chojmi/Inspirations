package com.github.chojmi.inspirations.presentation.common;

import com.github.chojmi.inspirations.domain.usecase.favorites.AddToFavorites;
import com.github.chojmi.inspirations.domain.usecase.favorites.RemoveFromFavorites;
import com.github.chojmi.inspirations.domain.utils.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class FavTogglerImpl implements FavToggler {
    private final AddToFavorites addToFavorites;
    private final RemoveFromFavorites removeFromFavorites;
    private final CompositeDisposable disposables;

    @Inject
    public FavTogglerImpl(@NonNull AddToFavorites addToFavorites, @NonNull RemoveFromFavorites removeFromFavorites) {
        this.addToFavorites = Preconditions.checkNotNull(addToFavorites);
        this.removeFromFavorites = Preconditions.checkNotNull(removeFromFavorites);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public Observable<Boolean> toggleFav(boolean isFav, @NonNull String photoId) {
        PublishSubject<Boolean> favState = PublishSubject.create();
        if (isFav) {
            disposables.remove(removeFromFavorites.process(photoId).subscribe(submitUiModel -> {
                if (submitUiModel.isInProgress()) {
                    return;
                }

                if (submitUiModel.isSucceed()) {
                    favState.onNext(false);
                } else {
                    favState.onError(submitUiModel.getError());
                }
            }, Timber::e));
        } else {
            disposables.add(addToFavorites.process(photoId).subscribe(submitUiModel -> {
                if (submitUiModel.isInProgress()) {
                    return;
                }

                if (submitUiModel.isSucceed()) {
                    favState.onNext(true);
                } else {
                    favState.onError(submitUiModel.getError());
                }
            }, Timber::e));
        }

        return favState;
    }

    public void onDestroy() {
        this.disposables.clear();
    }
}
