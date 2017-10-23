package com.github.chojmi.inspirations.presentation.gallery.ui.favs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.domain.utils.Preconditions;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;
import com.github.chojmi.inspirations.presentation.common.EmptyListView;
import com.github.chojmi.inspirations.presentation.utils.EndlessRecyclerViewScrollListener;
import com.github.chojmi.inspirations.presentation.utils.ViewUtils;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import timber.log.Timber;

public class FavsActivity extends BaseActivity implements FavsContract.View {
    private static final String ARG_PHOTO_ID = "ARG_PHOTO_ID";
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.back) ImageButton backBtn;
    @BindView(R.id.title) TextView titleView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.empty_list) EmptyListView emptyListView;
    @Inject FavsContract.Presenter favListPresenter;
    @Inject @Named("favs_adapter") BaseRecyclerViewAdapter<?, PersonEntity> adapter;

    public static Intent getCallingIntent(Context context, @NonNull String photoId) {
        Intent intent = new Intent(context, FavsActivity.class);
        intent.putExtra(ARG_PHOTO_ID, Preconditions.checkNotNull(photoId));
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_favs_activity);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(
                new EndlessRecyclerViewScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount) {
                        Timber.i("Load page: " + page);
                        favListPresenter.loadPage(page);
                    }
                });
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
        titleView.setText(getResources().getQuantityString(R.plurals.gallery_favs_title,
                photoFavs.getTotal(), photoFavs.getTotal()));
        adapter.setData(photoFavs.getPeople());
        ViewUtils.setVisibility(emptyListView, photoFavs.getTotal() == 0);
    }

    @Override
    public Observable<PersonEntity> getOnPersonSelectedObservable() {
        return adapter.getOnItemClickObservable();
    }

    @Override
    public Observable<View> getBackBtnClicksObservable() {
        return RxView.clicks(backBtn).map(o -> backBtn);
    }

    @Override
    public void showPerson(PersonEntity personEntity) {
        getNavigator().navigateToUserProfile(this, personEntity.getId());
    }

    @Override
    public void closeView() {
        finish();
    }

    @Override
    public void addItems(PhotoFavsEntity result) {
        adapter.addData(result.getPeople());
    }
}
