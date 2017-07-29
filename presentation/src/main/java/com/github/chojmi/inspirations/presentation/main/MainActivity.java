package com.github.chojmi.inspirations.presentation.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.pager) ViewPager mainViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mainViewPager.setAdapter(new MainScreenSlidePagerAdapter(this, getSupportFragmentManager()));
    }
}
