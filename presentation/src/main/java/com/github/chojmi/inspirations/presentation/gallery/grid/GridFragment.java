package com.github.chojmi.inspirations.presentation.gallery.grid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseFragment;
import com.github.chojmi.inspirations.presentation.main.MainActivity;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridFragment extends BaseFragment<MainActivity> implements GridContract.View {
    @BindView(R.id.rv_gallery) RecyclerView recyclerView;
    @Inject GridContract.Presenter presenter;
    private GridAdapter galleryAdapter;

    public static GridFragment newInstance() {
        return new GridFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInspirationsApp().createGalleryComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getInspirationsApp().releaseGalleryComponent();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        galleryAdapter = new GridAdapter(this);
        recyclerView.setAdapter(galleryAdapter);
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        galleryAdapter.setData(photos);
    }

    @Override
    public void openPhotoView(Photo photo) {
        getNavigator().navigateToPhoto(getContext(), photo);
    }

    @Override
    public void photoClicked(Photo photo) {
        presenter.photoSelected(photo);
    }
}
