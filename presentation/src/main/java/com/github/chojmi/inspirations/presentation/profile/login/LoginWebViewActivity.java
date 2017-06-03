package com.github.chojmi.inspirations.presentation.profile.login;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.chojmi.inspirations.presentation.BuildConfig;
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
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (BuildConfig.VERSION_CODE < Build.VERSION_CODES.LOLLIPOP) {
                    return !presenter.isPermittedUrl(url);
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }

            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (BuildConfig.VERSION_CODE >= Build.VERSION_CODES.LOLLIPOP) {
                    return !presenter.isPermittedUrl(request.getUrl().getQuery());
                } else {
                    return super.shouldOverrideUrlLoading(view, request);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                presenter.pageLoaded(url);
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
        webView.loadUrl(url);
    }

    @Override
    public void closeSuccessfully() {
        Intent result = new Intent();
        setResult(RESULT_OK, result);
        finish();
    }
}