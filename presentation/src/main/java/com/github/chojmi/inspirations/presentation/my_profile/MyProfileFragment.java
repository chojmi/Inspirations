package com.github.chojmi.inspirations.presentation.my_profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseFragment;
import com.github.chojmi.inspirations.presentation.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyProfileFragment extends BaseFragment<MainActivity> {
    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.btn_login)
    public void onLoginButtonClick(View view) {
        getNavigator().navigateToLoginWebView(getContext());
    }
}
