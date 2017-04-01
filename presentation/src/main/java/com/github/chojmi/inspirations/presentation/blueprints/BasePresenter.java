package com.github.chojmi.inspirations.presentation.blueprints;

import android.support.annotation.NonNull;

public interface BasePresenter<V extends BaseView> {

    void setView(@NonNull V view);

    void destroyView();
}
