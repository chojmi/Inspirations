package com.github.chojmi.inspirations.presentation.profile.login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;

public class LoginWebViewActivity extends BaseActivity implements LoginWebViewContract.View {
    @Inject LoginWebViewContract.Presenter presenter;
    @BindView(R.id.login_webview) LoginWebView loginWebView;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, LoginWebViewActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInspirationsApp().createLoginWebViewViewComponent().inject(this);
        setContentView(R.layout.activity_login_webview);
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
    protected void onDestroy() {
        super.onDestroy();
        getInspirationsApp().releaseLoginWebViewViewComponent();
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
}