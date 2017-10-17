package com.github.chojmi.inspirations.presentation.gallery.ui.fav_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.domain.utils.Preconditions;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import dagger.android.AndroidInjection;
import io.reactivex.annotations.NonNull;

public class FavListActivity extends BaseActivity implements FavListContract.View {
    private static final String ARG_PHOTO_ID = "ARG_PHOTO_ID";
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @Inject FavListContract.Presenter favListPresenter;
    @Inject @Named("fav_list_adapter") BaseRecyclerViewAdapter<?, PersonEntity> adapter;

    public static Intent getCallingIntent(Context context, @NonNull String photoId) {
        Intent intent = new Intent(context, FavListActivity.class);
        intent.putExtra(ARG_PHOTO_ID, Preconditions.checkNotNull(photoId));
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_fav_list_activity);
        recyclerView.setAdapter(adapter);
        favListPresenter.setView(this);
    }

    public String getArgPhotoId() {
        return getIntent().getStringExtra(ARG_PHOTO_ID);
    }

    @Override
    public void toggleProgressBar(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void renderView(PhotoFavsEntity photoFavs) {
        adapter.setData(photoFavs.getPeople());
    }
}
