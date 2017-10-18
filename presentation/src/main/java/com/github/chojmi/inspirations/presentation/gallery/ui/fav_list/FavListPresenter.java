package com.github.chojmi.inspirations.presentation.gallery.ui.fav_list;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.usecase.photos.GetPhotoFavs;
import com.github.chojmi.inspirations.domain.utils.Preconditions;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class FavListPresenter implements FavListContract.Presenter {

    private final String photoId;
    private final GetPhotoFavs getPhotoFavs;
    private final CompositeDisposable disposables;
    private FavListContract.View view;

    @Inject
    public FavListPresenter(@NonNull String photoId, GetPhotoFavs getPhotoFavs) {
        this.photoId = Preconditions.checkNotNull(photoId);
        this.getPhotoFavs = Preconditions.checkNotNull(getPhotoFavs);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void setView(FavListContract.View view) {
        this.view = checkNotNull(view);
        fetchFavList();
        disposables.add(view.getOnPersonSelectedObservable().subscribe(view::showPerson, Timber::e));
        disposables.add(view.getBackBtnClicksObservable().subscribe(v -> view.closeView(), Timber::e));
    }

    private void fetchFavList() {
        getPhotoFavs.process(GetPhotoFavs.Args.create(photoId)).subscribe(submitUiModel -> {
            view.toggleProgressBar(submitUiModel.isInProgress());
            if (submitUiModel.isInProgress()) {
                return;
            }
            if (submitUiModel.isSucceed()) {
                view.renderView(submitUiModel.getResult());
            }
        });
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
