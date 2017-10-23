package com.github.chojmi.inspirations.presentation.profile.my_profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseFragment;
import com.github.chojmi.inspirations.presentation.main.MainActivity;
import com.github.chojmi.inspirations.presentation.profile.user_profile.UserProfileView;
import com.github.chojmi.inspirations.presentation.profile.user_profile.UserPublicPhotosContract;
import com.github.chojmi.inspirations.presentation.profile.user_profile.UserPublicPhotosView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class MyProfileFragment extends BaseFragment<MainActivity> implements MyProfileContract.View {
    private static final int LOGIN_REQUEST_CODE = 1000;
    @Inject MyProfileContract.Presenter presenter;
    @Inject UserPublicPhotosContract.Presenter userUserPublicPhotosPresenter;
    @BindView(R.id.user_profile) UserProfileView userProfileView;
    @BindView(R.id.login_screen) ViewGroup loginView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.user_public_photos) UserPublicPhotosView userPublicPhotosView;

    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
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
    public void showProfile(PersonEntity personEntity) {
        userUserPublicPhotosPresenter.setUserId(personEntity.getId());
        userUserPublicPhotosPresenter.setView(userPublicPhotosView);
        userProfileView.renderProfile(personEntity);
        userProfileView.setVisibility(View.VISIBLE);
        loginView.setVisibility(View.GONE);
    }

    @Override
    public void toggleProgressBar(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}