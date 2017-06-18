package com.github.chojmi.inspirations.presentation.profile.login;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginWebView extends WebView {
    private Interactor interactor;

    public LoginWebView(Context context) {
        super(context);
        init();
    }

    public LoginWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setInteractor(Interactor interactor) {
        this.interactor = interactor;
    }

    private void init() {
        setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (BuildConfig.VERSION_CODE < Build.VERSION_CODES.LOLLIPOP) {
//                    return !presenter.isPermittedUrl(url);
//                } else {
//                    return super.shouldOverrideUrlLoading(view, url);
//                }
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                if (BuildConfig.VERSION_CODE >= Build.VERSION_CODES.LOLLIPOP) {
//                    return !presenter.isPermittedUrl(request.getUrl().getQuery());
//                } else {
//                    return super.shouldOverrideUrlLoading(view, request);
//                }
//            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
//                presenter.pageLoaded(url);
            }
        });
        getSettings().setJavaScriptEnabled(true);
        addJavascriptInterface(new TokenFetcher(token -> {
            if (interactor != null) {
                interactor.tokenObtained(token);
            }
        }), "HTMLOUT");
    }

    interface Interactor {
        void tokenObtained(String token);
    }
}
