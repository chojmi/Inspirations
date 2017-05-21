package com.github.chojmi.inspirations.presentation.model.gallery;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

@AutoValue
public abstract class GridAdapterUiModel {
    public static GridAdapterUiModel create(Photo photo) {
        return new AutoValue_GridAdapterUiModel(photo, null, null);
    }

    public static List<GridAdapterUiModel> create(List<Photo> photos) {
        List<GridAdapterUiModel> uiModels = new ArrayList<>();
        Observable.fromIterable(photos)
                .map(GridAdapterUiModel::create)
                .toList()
                .subscribe(uiModels::addAll, Timber::e);
        return uiModels;
    }

    public static GridAdapterUiModel setFavs(GridAdapterUiModel model, PhotoFavs favs) {
        return new AutoValue_GridAdapterUiModel(model.getPhoto(), favs, model.getComments());
    }

    public static GridAdapterUiModel setComments(GridAdapterUiModel model, PhotoComments comments) {
        return new AutoValue_GridAdapterUiModel(model.getPhoto(), model.getFavs(), comments);
    }

    public abstract Photo getPhoto();

    @Nullable
    public abstract PhotoFavs getFavs();

    @Nullable
    public abstract PhotoComments getComments();
}
