package com.github.chojmi.inspirations.presentation.blueprints;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.chojmi.inspirations.presentation.ApplicationComponent;
import com.github.chojmi.inspirations.presentation.InspirationsApp;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements IBaseUI {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((InspirationsApp) getApplication()).getApplicationComponent();
    }
}
