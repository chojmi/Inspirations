package com.github.chojmi.inspirations.data.source.remote;

import com.github.chojmi.presentation.data.BuildConfig;

import java.util.HashMap;
import java.util.Map;

abstract class BaseRemoteDataSource {

    protected Map<String, String> getBaseArgs(String method) {
        Map<String, String> args = new HashMap<>();
        args.put("api_key", BuildConfig.API_KEY);
        args.put("format", "json");
        args.put("method", method);
        return args;
    }
}
