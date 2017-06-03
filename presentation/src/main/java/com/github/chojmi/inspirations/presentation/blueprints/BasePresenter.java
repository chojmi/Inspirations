package com.github.chojmi.inspirations.presentation.blueprints;

import io.reactivex.annotations.NonNull;

public interface BasePresenter<V extends BaseView> {

    void setView(@NonNull V view);

    void destroyView();
}
