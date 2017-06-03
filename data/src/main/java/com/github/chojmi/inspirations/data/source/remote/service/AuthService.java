package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.auth.FrobEntityImpl;
import com.github.chojmi.inspirations.data.entity.auth.TokenEntityImpl;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface AuthService {
    @GET("services/rest")
    Observable<FrobEntityImpl> getFrob(@QueryMap Map<String, String> options);

    @GET("services/rest")
    Observable<TokenEntityImpl> getToken(@QueryMap Map<String, String> options);
}
