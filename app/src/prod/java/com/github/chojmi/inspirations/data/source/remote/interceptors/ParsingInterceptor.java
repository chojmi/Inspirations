package com.github.chojmi.inspirations.data.source.remote.interceptors;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ParsingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(parseToJson(response.body())).build();
    }

    private ResponseBody parseToJson(ResponseBody responseBody) throws IOException {
//        Pattern pattern = Pattern.compile("jsonFlickrApi*");
        Pattern pattern = Pattern.compile("/.");
        Matcher matcher = pattern.matcher(responseBody.string());
//        return ResponseBody.create(responseBody.contentType(), matcher.group());
        return responseBody;
    }
}
