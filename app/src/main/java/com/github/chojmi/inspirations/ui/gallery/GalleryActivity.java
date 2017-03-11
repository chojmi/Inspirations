package com.github.chojmi.inspirations.ui.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.chojmi.inspirations.InspirationsApp;
import com.github.chojmi.inspirations.R;
import com.github.chojmi.inspirations.ui.blueprints.BaseActivity;

import javax.inject.Inject;

public class GalleryActivity extends BaseActivity {

    @Inject
    GalleryPresenter galleryPresenter;
    GalleryFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragment = (GalleryFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        DaggerGalleryComponent.builder()
                .galleryRepositoryComponent(((InspirationsApp) getApplication()).getGalleryRepositoryComponent())
                .galleryPresenterModule(new GalleryPresenterModule(fragment))
                .build()
                .inject(this);
    }
}
