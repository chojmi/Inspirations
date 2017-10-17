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

import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeListEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseFragment;
import com.github.chojmi.inspirations.presentation.gallery.model.GridAdapterUiModel;
import com.github.chojmi.inspirations.presentation.gallery.model.Person;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoComments;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;
import com.github.chojmi.inspirations.presentation.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.CompositeDisposable;

public class GridFragment extends BaseFragment<MainActivity> implements GridPhotoContract.View, GridPhotoAttrsContract.View, GridAdapter.Listener {
    @BindView(R.id.rv_gallery) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @Inject GridPhotoContract.Presenter photoPresenter;
    @Inject GridPhotoAttrsContract.Presenter photoAttrsPresenter;
    private GridAdapter galleryAdapter;
    private CompositeDisposable disposables;

    public static GridFragment newInstance() {
        return new GridFragment();
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
        disposables.add(galleryAdapter.getCommentsIconClicksObservable().subscribe(photo -> photoPresenter.commentIconSelected(photo)));
        disposables.add(galleryAdapter.getFavsClicksObservable().subscribe(photo -> photoPresenter.favsSelected(photo)));
        disposables.add(galleryAdapter.getFavsIconClicksObservable().subscribe(photo -> photoPresenter.favIconSelected(photo)));
        recyclerView.setAdapter(galleryAdapter);
    }

    @Override
    public void showPhotos(List<PhotoWithAuthor> photos) {
        galleryAdapter.setData(GridAdapterUiModel.Companion.create(photos));
    }

    @Override
    public void openPhotoView(ImageView imageView, PhotoWithAuthor photo) {
        getNavigator().navigateToPhoto(getActivity(), photo, imageView);
    }

    @Override
    public void openUserProfile(Person person) {
        getNavigator().navigateToUserProfile(getContext(), person.getId());
    }

    @Override
    public void showFavs(Photo photo) {
        getNavigator().navigateToPhotoFavsList(getContext(), photo.getId());
    }

    @Override
    public void showComments(PhotoWithAuthor photo) {

    }

    @Override
    public void toggleFav(PhotoWithAuthor photo) {

    }

    @Override
    public void addComment(PhotoWithAuthor photo) {

    }

    @Override
    public void toggleProgressBar(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showFavs(int position, PhotoFavs photoFavs) {
        galleryAdapter.setFavs(position, photoFavs);
    }

    @Override
    public void showComments(int position, PhotoComments photoComments) {
        galleryAdapter.setComments(position, photoComments);
    }

    @Override
    public void showPhotoSizes(int position, PhotoSizeListEntity sizeList) {
        galleryAdapter.setPhotoSizes(position, sizeList);
    }

    @Override
    public void onNewItemBind(int position, PhotoWithAuthor photo) {
        photoAttrsPresenter.loadPhotoSizes(position, photo);
        photoAttrsPresenter.loadComments(position, photo);
        photoAttrsPresenter.loadFavs(position, photo);
    }
}
