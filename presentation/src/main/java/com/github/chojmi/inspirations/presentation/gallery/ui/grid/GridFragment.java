package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeListEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseFragment;
import com.github.chojmi.inspirations.presentation.gallery.model.GridAdapterUiModel;
import com.github.chojmi.inspirations.presentation.gallery.model.Person;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;
import com.github.chojmi.inspirations.presentation.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class GridFragment extends BaseFragment<MainActivity> implements GridPhotoContract.View, GridPhotoAttrsContract.View, GridAdapter.Listener {
    private static final String ARG_ITEM_TO_REFRESH = "ARG_ITEM_TO_REFRESH";
    @BindView(R.id.rv_gallery) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @Inject GridPhotoContract.Presenter photoPresenter;
    @Inject GridPhotoAttrsContract.Presenter photoAttrsPresenter;
    private GridAdapter galleryAdapter;
    private CompositeDisposable disposables;

    public static GridFragment newInstance() {
        GridFragment gridFragment = new GridFragment();
        gridFragment.setArguments(new Bundle());
        return gridFragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gallery_fragment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.disposables = new CompositeDisposable();
        initRecyclerView();
        photoPresenter.setView(this);
        photoAttrsPresenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initialActions();
    }

    private void initialActions() {
        int itemToRefresh = getArguments().getInt(ARG_ITEM_TO_REFRESH, -1);
        getArguments().remove(ARG_ITEM_TO_REFRESH);
        if (galleryAdapter.getItemCount() > itemToRefresh && itemToRefresh != -1) {
            refreshPhotoInfo(itemToRefresh);
        }
    }

    private void refreshPhotoInfo(int position) {
        photoAttrsPresenter.loadPhotoInfo(position, galleryAdapter.getItem(position).getPhoto());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        photoPresenter.destroyView();
        photoAttrsPresenter.destroyView();
        disposables.dispose();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        galleryAdapter = new GridAdapter(this);
        disposables.add(galleryAdapter.getPhotoClicksObservable().subscribe(pair -> photoPresenter.photoSelected(pair.first, pair.second)));
        disposables.add(galleryAdapter.getProfileClicksObservable().subscribe(photo -> photoPresenter.profileSelected(photo.getPerson())));
        disposables.add(galleryAdapter.getCommentsClicksObservable().subscribe(photo -> photoPresenter.commentsSelected(photo)));
        disposables.add(galleryAdapter.getFavsClicksObservable().subscribe(photo -> photoPresenter.favsSelected(photo)));
        disposables.add(galleryAdapter.getFavsIconClicksObservable().subscribe(position ->
                photoPresenter.favIconSelected(position, galleryAdapter.getItem(position).getPhotoInfo())));
        recyclerView.setAdapter(galleryAdapter);
    }

    @Override
    public void showPhotos(List<PhotoWithAuthor> photos) {
        galleryAdapter.setData(GridAdapterUiModel.Companion.create(photos));
    }

    @Override
    public void openPhotoView(int position, ImageView imageView) {
        getArguments().putInt(ARG_ITEM_TO_REFRESH, position);
        getNavigator().navigateToPhoto(getActivity(), galleryAdapter.getItem(position).getPhoto(), imageView);
    }

    @Override
    public void openUserProfile(Person person) {
        getNavigator().navigateToUserProfile(getContext(), person.getId());
    }

    @Override
    public void goToFavs(Photo photo) {
        getNavigator().navigateToPhotoFavs(getContext(), photo.getId());
    }

    @Override
    public void showComments(PhotoWithAuthor photo) {
        getNavigator().navigateToComments(getContext(), photo.getPhoto().getId());
    }

    @Override
    public void toggleProgressBar(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void refreshFavSelection(int position, Observable<Boolean> isFav) {
        disposables.add(isFav.subscribe(result -> refreshPhotoInfo(position), Timber::e));
    }

    @Override
    public void showFavs(int position, PhotoFavs photoFavs) {
        galleryAdapter.setFavs(position, photoFavs);
    }

    @Override
    public void showPhotoInfo(int position, PhotoInfoEntity photoInfo) {
        galleryAdapter.showPhotoInfo(position, photoInfo);
    }

    @Override
    public void showPhotoSizes(int position, PhotoSizeListEntity sizeList) {
        galleryAdapter.setPhotoSizes(position, sizeList);
    }

    @Override
    public void onNewItemBind(int position, PhotoWithAuthor photo) {
        photoAttrsPresenter.loadPhotoSizes(position, photo);
        photoAttrsPresenter.loadPhotoInfo(position, photo);
        photoAttrsPresenter.loadFavs(position, photo);
    }
}
