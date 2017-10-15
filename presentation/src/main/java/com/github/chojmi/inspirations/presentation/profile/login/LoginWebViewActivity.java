package com.github.chojmi.inspirations.presentation.profile.login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ProgressBar;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjection;
import io.reactivex.annotations.Nullable;

public class LoginWebViewActivity extends BaseActivity implements LoginWebViewContract.View {
    @Inject LoginWebViewContract.Presenter presenter;
    @BindView(R.id.login_webview) LoginWebView loginWebView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, LoginWebViewActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_login_webview_activity);
        loginWebView.setInteractor(token -> {
            if (!token.isEmpty()) {
                presenter.onVerifierTokenObtained(token);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.destroyView();
    }

    @Override
    public void loadLoginPage(String url) {
        loginWebView.loadUrl(url);
    }

    @Override
    public void closeSuccessfully() {
        Intent result = new Intent();
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public void toggleProgressBar(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}