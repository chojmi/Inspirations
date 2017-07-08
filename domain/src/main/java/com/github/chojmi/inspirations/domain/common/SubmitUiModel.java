package com.github.chojmi.inspirations.domain.common;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubmitUiModel<?> that = (SubmitUiModel<?>) o;

        if (inProgress != that.inProgress) return false;
        if (succeed != that.succeed) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        return error != null ? error.equals(that.error) : that.error == null;
    }

    @Override
    public int hashCode() {
        int result1 = (inProgress ? 1 : 0);
        result1 = 31 * result1 + (succeed ? 1 : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (error != null ? error.hashCode() : 0);
        return result1;
    }
}