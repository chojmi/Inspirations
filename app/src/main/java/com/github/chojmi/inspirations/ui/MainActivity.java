package com.github.chojmi.inspirations.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chojmi.inspirations.InspirationsApp;
import com.github.chojmi.inspirations.R;
import com.github.chojmi.inspirations.ui.blueprints.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.button)
    public void onButtonClick(Button button) {
        ((InspirationsApp) getApplication()).getGalleryRepositoryComponent().getGalleryRepository().loadGallery("72157677898551390").subscribe(gallery -> {
            Glide.with(this).load(gallery.getPhoto().get(1).getUrl()).into(imageView);
        }, Timber::e);
    }
}
