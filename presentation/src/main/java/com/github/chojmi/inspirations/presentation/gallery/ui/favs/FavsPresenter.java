package com.github.chojmi.inspirations.presentation.gallery.ui.favs;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.domain.utils.Preconditions;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class FavsPresenter implements FavsContract.Presenter {

    private final String photoId;
    private final GetPhotoFavs getPhotoFavs;
    private CompositeDisposable disposables;
    private FavsContract.View view;

    @Inject
    public FavsPresenter(@NonNull String photoId, GetPhotoFavs getPhotoFavs) {
        this.photoId = Preconditions.checkNotNull(photoId);
        this.getPhotoFavs = Preconditions.checkNotNull(getPhotoFavs);
    }

    @Override
    public void setView(FavsContract.View view) {
        this.disposables = new CompositeDisposable();
        this.view = checkNotNull(view);
        fetchFavs();
        disposables.add(view.getOnPersonSelectedObservable().subscribe(view::showPerson, Timber::e));
        disposables.add(view.getBackBtnClicksObservable().subscribe(v -> view.closeView(), Timber::e));
    }

    private void fetchFavs() {
        disposables.add(getPhotoFavs.process(GetPhotoFavs.Args.create(photoId)).subscribe(submitUiModel -> {
            view.toggleProgressBar(submitUiModel.isInProgress());
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.renderView(submitUiModel.getResult());
            }
        }, Timber::e));
    }

    @Override
    public void destroyView() {
        this.disposables.clear();
        this.view = null;
    }

    @Override
    public void loadPage(int page) {
        getPhotoFavs.process(GetPhotoFavs.Args.create(photoId, page)).subscribe(submitUiModel -> {
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.addItems(submitUiModel.getResult());
            }
        });
    }
}
