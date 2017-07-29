package com.github.chojmi.inspirations.presentation.search.photos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.photo.item.PhotoViewActivity;
import com.github.chojmi.inspirations.presentation.photo.list.PhotoListAdapter;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchPhotosView extends LinearLayoutCompat
        implements SearchPhotosContract.View {
    @Inject SearchPhotosContract.Presenter presenter;
    @BindView(R.id.search_view) SearchView searchView;
    @BindView(R.id.rv_search) RecyclerView recyclerView;

    private PhotoListAdapter photoListAdapter;

    public SearchPhotosView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        RxSearchView.queryTextChanges(searchView)
                .startWith("birds")
                .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .throttleLast(200, TimeUnit.DAYS.MILLISECONDS)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(charSequence -> presenter.search(charSequence.toString()));
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        photoListAdapter = new PhotoListAdapter();
        recyclerView.setAdapter(photoListAdapter);
        photoListAdapter.getPhotoClicksSubject()
                .subscribe(photo -> getContext().startActivity(PhotoViewActivity.getCallingIntent(getContext(), photo)));
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
    public void renderView(List<Photo> photos) {
        photoListAdapter.setData(photos);
    }
}
