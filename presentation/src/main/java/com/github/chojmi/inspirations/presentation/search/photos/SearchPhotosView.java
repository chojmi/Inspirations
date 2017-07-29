package com.github.chojmi.inspirations.presentation.search.photos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchPhotosView extends LinearLayout implements SearchPhotosContract.View {
    @Inject SearchPhotosContract.Presenter presenter;
    @BindView(R.id.search_view) SearchView searchView;

    public SearchPhotosView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        RxSearchView.queryTextChanges(searchView)
                .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .throttleLast(200, TimeUnit.DAYS.MILLISECONDS)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(charSequence -> presenter.search(charSequence.toString()));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            presenter.setView(this);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (presenter != null) {
            presenter.destroyView();
        }
    }

    @Override
    public void renderView(GalleryEntity gallery) {
    }
}
