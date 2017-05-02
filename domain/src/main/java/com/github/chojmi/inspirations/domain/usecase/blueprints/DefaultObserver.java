package com.github.chojmi.inspirations.domain.usecase.blueprints;

import io.reactivex.ObservableEmitter;
import io.reactivex.observers.DisposableObserver;

public class DefaultObserver<T> extends DisposableObserver<T> {
    private ObservableEmitter<T> e;

    private DefaultObserver(ObservableEmitter<T> e) {
        this.e = e;
    }

    public static <T> DefaultObserver<T> create() {
        return new DefaultObserver<>(null);
    }

    public static <T> DefaultObserver<T> create(ObservableEmitter<T> e) {
        return new DefaultObserver<>(e);
    }

    @Override
    public void onNext(T t) {
        if (e != null) {
            e.onNext(t);
        }
    }

    @Override
    public void onComplete() {
        if (e != null) {
            e.onComplete();
        }
    }

    @Override
    public void onError(Throwable exception) {
        if (e != null) {
            e.onError(exception);
        }
    }
}
