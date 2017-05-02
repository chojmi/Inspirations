package com.github.chojmi.inspirations.presentation.model.gallery;

import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitUiModel;
import com.google.auto.value.AutoValue;

import java.util.List;

import io.reactivex.annotations.Nullable;

@AutoValue
public abstract class PhotoSubmitUiModel extends BaseSubmitUiModel {
    public static PhotoSubmitUiModel inProgress() {
        return new AutoValue_PhotoSubmitUiModel(true, false, null, null);
    }

    public static PhotoSubmitUiModel failure(Throwable t) {
        return new AutoValue_PhotoSubmitUiModel(false, false, t, null);
    }

    public static PhotoSubmitUiModel success(List<Photo> photos) {
        return new AutoValue_PhotoSubmitUiModel(false, true, null, photos);
    }

    @Nullable
    public abstract List<Photo> getPhotos();
}