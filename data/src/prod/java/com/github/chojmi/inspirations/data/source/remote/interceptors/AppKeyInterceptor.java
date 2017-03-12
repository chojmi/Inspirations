package com.github.chojmi.presentation.data.source.remote.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AppKeyInterceptor implements Interceptor {

    private final String publicAppKey;

    public AppKeyInterceptor(String publicAppKey) {
        this.publicAppKey = publicAppKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request request = originalRequest.newBuilder()
//                .url(originalRequest.url().toString() + "appkey/" + publicAppKey + "/")
                .build();
        return chain.proceed(request);
    }
}
