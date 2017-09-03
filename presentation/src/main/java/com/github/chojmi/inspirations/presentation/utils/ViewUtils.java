package com.github.chojmi.inspirations.presentation.utils;

import android.view.View;

public final class ViewUtils {
    private ViewUtils() {
        throw new AssertionError();
    }

    public static void setVisibility(View view, boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}
