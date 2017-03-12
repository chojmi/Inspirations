package com.github.chojmi.inspirations.presentation.blueprints;

import android.support.annotation.NonNull;

public interface BaseView<T> {

    void setPresenter(@NonNull T presenter);

    boolean isActive();
}
