package com.github.chojmi.inspirations.domain.usecase.blueprints;

import io.reactivex.annotations.Nullable;

public abstract class BaseSubmitUiModel {
    public abstract boolean isInProgress();

    public abstract boolean isSuccess();

    @Nullable
    public abstract Throwable getErrorMessage();
}