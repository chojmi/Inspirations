package com.github.chojmi.inspirations.data.source.remote.interceptors;

import com.github.chojmi.inspirations.data.source.remote.service.OAuthService;
import com.github.chojmi.inspirations.domain.utils.RequestNotAllowedException;

import java.io.IOException;

import io.reactivex.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class SigningInterceptor implements Interceptor {
    private final OAuthService oAuthService;

    public SigningInterceptor(@NonNull OAuthService oAuthService) {
        this.oAuthService = checkNotNull(oAuthService);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String signedUrl;
        switch (request.method()) {
            case "GET":
                if (oAuthService.containsAccessToken()) {
                    signedUrl = oAuthService.signGetRequest(request.url().toString());
                } else {
                    return chain.proceed(request);
                }
                break;
            case "POST":
                if (oAuthService.containsAccessToken()) {
                    signedUrl = oAuthService.signPostRequest(request.url().toString());
                } else {
                    throw new RequestNotAllowedException();
                }
                break;
            default:
                return chain.proceed(request);
        }
        return chain.proceed(request.newBuilder().url(signedUrl).build());
    }
}
