package com.github.chojmi.inspirations.presentation.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.chojmi.inspirations.presentation.InspirationsApp;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;

import javax.inject.Inject;

public class GalleryActivity extends BaseActivity {

    @Inject
    GalleryPresenter galleryPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GalleryFragment fragment = (GalleryFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        DaggerGalleryComponent.builder()
                .galleryRepositoryComponent(((InspirationsApp) getApplication()).getGalleryRepositoryComponent())
                .galleryPresenterModule(new GalleryPresenterModule(fragment))
                .build()
                .inject(this);
    }
}
