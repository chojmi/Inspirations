package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.auth.LoginDataEntityImpl;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface AuthTestService {
    @GET("services/rest")
    Flowable<LoginDataEntityImpl> loadLoginData(@QueryMap Map<String, String> options);
}
