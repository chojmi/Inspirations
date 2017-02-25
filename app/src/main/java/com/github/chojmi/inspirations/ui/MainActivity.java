package com.github.chojmi.inspirations.ui;

import android.os.Bundle;
import android.widget.Button;

import com.github.chojmi.inspirations.InspirationsApp;
import com.github.chojmi.inspirations.R;
import com.github.chojmi.inspirations.ui.blueprints.BaseActivity;

import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.button)
    public void onButtonClick(Button button) {
        ((InspirationsApp) getApplication()).getGalleryRepositoryComponent().getGalleryRepository().loadGallery("72157677898551390").subscribe(gallery -> {
            gallery.toString();
        }, Timber::e);
    }
}
