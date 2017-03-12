package com.github.chojmi.inspirations.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;
import com.github.chojmi.inspirations.presentation.gallery.GalleryActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.button)
    public void onButtonClick(Button button) {
        startActivity(new Intent(this, GalleryActivity.class));
    }
}
