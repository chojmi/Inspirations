package com.github.chojmi.inspirations.presentation.blueprints;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.github.chojmi.inspirations.presentation.InspirationsApp;
import com.github.chojmi.inspirations.presentation.main.Navigator;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements IBaseUI {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public Navigator getNavigator() {
        return getInspirationsApp().getNavigator();
    }

    @Override
    public InspirationsApp getInspirationsApp() {
        return ((InspirationsApp) getApplication());
    }
}
