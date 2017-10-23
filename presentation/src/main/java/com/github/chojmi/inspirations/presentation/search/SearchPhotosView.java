package com.github.chojmi.inspirations.presentation.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.common.EmptyListView;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.photo.PhotoListAdapter;
import com.github.chojmi.inspirations.presentation.utils.ViewUtils;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class SearchPhotosView extends FrameLayout implements SearchPhotosContract.View {
    @BindView(R.id.search_view) SearchView searchView;
    @BindView(R.id.rv_search) RecyclerView recyclerView;
    @BindView(R.id.empty_list) EmptyListView emptyListView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private PhotoListAdapter photoListAdapter;

    public SearchPhotosView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.search_photos_view, this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        photoListAdapter = new PhotoListAdapter();
        recyclerView.setAdapter(photoListAdapter);
    }

    @Override
    public Observable<CharSequence> getSearchObservable() {
        return RxSearchView.queryTextChanges(searchView);
    }

    @Override
    public Observable<Pair<ImageView, Photo>> getPhotoClicksSubject() {
        return photoListAdapter.getPhotoClicksSubject();
    }

    @Override
    public void renderView(List<Photo> photos) {
        photoListAdapter.setData(photos);
        ViewUtils.setVisibility(emptyListView, photos.size() == 0);
    }

    @Override
    public void toggleProgressBar(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
