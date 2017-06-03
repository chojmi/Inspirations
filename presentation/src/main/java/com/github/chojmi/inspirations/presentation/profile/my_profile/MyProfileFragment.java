package com.github.chojmi.inspirations.presentation.profile.my_profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseFragment;
import com.github.chojmi.inspirations.presentation.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyProfileFragment extends BaseFragment<MainActivity> implements MyProfileContract.View {
    private static final int LOGIN_REQUEST_CODE = 1000;
    @Inject MyProfileContract.Presenter presenter;
    @BindView(R.id.tv_name) TextView name;
    @BindView(R.id.btn_login) Button loginAction;

    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInspirationsApp().createMyProfileComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.destroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getInspirationsApp().releaseMyProfileComponent();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != LOGIN_REQUEST_CODE) {
            return;
        }
        if (resultCode == Activity.RESULT_OK) {
            presenter.loginSuccessful();
        }
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick(View view) {
        getNavigator().navigateToLoginWebView(this, LOGIN_REQUEST_CODE);
    }

    @Override
    public void isLoggedIn(boolean isLoggedIn) {
        name.setVisibility(isLoggedIn ? View.VISIBLE : View.GONE);
        loginAction.setVisibility(isLoggedIn ? View.GONE : View.VISIBLE);
    }

    @Override
    public void renderProfile(String name) {
        this.name.setText(name);
    }
}
