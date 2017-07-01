package com.github.chojmi.inspirations.presentation.profile.login;

import android.webkit.JavascriptInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.annotations.NonNull;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

class TokenFetcher {
    private final Listener listener;

    TokenFetcher(@NonNull Listener listener) {
        this.listener = checkNotNull(listener);
    }

    @JavascriptInterface
    public void processHTML(String html) {
        Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{3}");
        Matcher matcher = pattern.matcher(html);
        String result = "";
        if (matcher.find()) {
            result = matcher.group(0);
        }
        listener.fetchedToken(result);
    }

    interface Listener {
        void fetchedToken(String token);
    }
}
