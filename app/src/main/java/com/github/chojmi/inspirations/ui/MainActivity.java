package com.github.chojmi.inspirations.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.github.chojmi.inspirations.R;
import com.github.chojmi.inspirations.ui.blueprints.BaseActivity;
import com.github.chojmi.inspirations.ui.gallery.GalleryActivity;

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
