package com.github.chojmi.presentation.data.source.remote.interceptors;

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
        return ResponseBody.create(responseBody.contentType(), parseHeadline(responseBody.string()));
    }

    private String parseHeadline(String input) {
        Matcher matcher = Pattern.compile("^\\w+\\p{Punct}(.*)\\p{Punct}$").matcher(input);
        if (matcher.find()) {
            return parseStat(matcher.group(1));
        }
        return input;
    }

    private String parseStat(String input) {
        Matcher matcher = Pattern.compile("^\\p{Punct}\"\\w+\"\\p{Punct}(.*)\\p{Punct}\"stat\".*\\p{Punct}$").matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return input;
    }
}
