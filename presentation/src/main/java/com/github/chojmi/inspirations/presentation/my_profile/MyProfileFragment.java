package com.github.chojmi.inspirations.presentation.my_profile;

import android.app.Activity;
import android.content.Intent;
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
    private static final int LOGIN_REQUEST_CODE = 1000;

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
        getNavigator().navigateToLoginWebView(this, LOGIN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != LOGIN_REQUEST_CODE) {
            return;
        }
        if (resultCode == Activity.RESULT_OK) {

        }
    }
}
