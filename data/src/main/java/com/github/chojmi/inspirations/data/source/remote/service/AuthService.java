package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.auth.LoginDataEntityImpl;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface AuthService {
    @GET("services/rest")
    Observable<LoginDataEntityImpl> loadLoginData(@QueryMap Map<String, String> options);

}
