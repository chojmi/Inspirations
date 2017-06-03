package com.github.chojmi.inspirations.presentation.my_profile.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;

public class LoginWebViewActivity extends BaseActivity implements LoginWebViewContract.View {
    @Inject LoginWebViewContract.Presenter presenter;
    @BindView(R.id.webview) WebView webView;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, LoginWebViewActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInspirationsApp().createLoginWebViewViewComponent().inject(this);
        setContentView(R.layout.webview);
        webView.setWebViewClient(new WebViewClient());
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
        getInspirationsApp().releasePhotoViewComponent();
    }

    @Override
    public void loadLoginPage(String url) {
        webView.loadUrl(url);
    }
}