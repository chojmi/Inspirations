package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.auth.LoginDataEntityImpl;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.QueryMap;

public class FakeAuthTestService implements AuthTestService {
    @Override
    public Flowable<LoginDataEntityImpl> loadLoginData(@QueryMap Map<String, String> options) {
        return  Flowable.empty();
    }
}
