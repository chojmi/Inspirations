package com.github.chojmi.inspirations.presentation.profile.my_profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseFragment;
import com.github.chojmi.inspirations.presentation.main.MainActivity;
import com.github.chojmi.inspirations.presentation.profile.user_profile.UserProfileComponent;
import com.github.chojmi.inspirations.presentation.profile.user_profile.UserProfileView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyProfileFragment extends BaseFragment<MainActivity> implements MyProfileContract.View {
    private static final int LOGIN_REQUEST_CODE = 1000;
    @Inject MyProfileContract.Presenter presenter;
    @BindView(R.id.user_profile) UserProfileView userProfileView;
    @BindView(R.id.login_screen) ViewGroup loginView;

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
        View v = inflater.inflate(R.layout.profile_user_fragment, container, false);
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
        getInspirationsApp().releaseUserProfileComponent();
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
    public void showProfile(UserEntity userEntity) {
        UserProfileComponent userProfileComponent = getInspirationsApp().createUserProfileComponent(userEntity.getId());
        userProfileComponent.inject(userProfileView);
        userProfileView.setUserProfileComponent(userProfileComponent);
        userProfileView.setVisibility(View.VISIBLE);
        loginView.setVisibility(View.GONE);
    }
}
