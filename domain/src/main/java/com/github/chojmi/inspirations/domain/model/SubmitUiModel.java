package com.github.chojmi.inspirations.domain.model;

import io.reactivex.annotations.Nullable;

public class SubmitUiModel<T> {
    private final boolean inProgress, succeed;
    private final T result;
    private final Throwable error;

    private SubmitUiModel(boolean inProgress, boolean succeed, T result, Throwable error) {
        this.inProgress = inProgress;
        this.succeed = succeed;
        this.result = result;
        this.error = error;
    }

    public static <T> SubmitUiModel<T> success(T result) {
        return new SubmitUiModel<>(false, true, result, null);
    }

    public static <T> SubmitUiModel<T> inProgress() {
        return new SubmitUiModel<>(true, false, null, null);
    }

    public static <T> SubmitUiModel<T> fail(Throwable error) {
        return new SubmitUiModel<>(false, false, null, error);
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public boolean isSucceed() {
        return succeed;
    }

    @Nullable
    public T getResult() {
        return result;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }
}