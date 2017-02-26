package com.github.chojmi.inspirations.data.source.remote.interceptors;

import org.junit.Test;

import java.io.IOException;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static org.junit.Assert.*;

public class ParsingInterceptorTest {
    @Test
    public void responseIsParsedCorrectly() throws Exception {
        Response response = testInterception("jsonFlickrApi({CONTENT},\"stat\":\"ok\"})");
        assertEquals(response.body().string(), "witam");
    }

    private Response testInterception(String responseBody) throws IOException {
        return new ParsingInterceptor().intercept(new Interceptor.Chain() {
            @Override
            public Request request() {
                return new Request.Builder().url("https://localhost:1/").build();
            }
            @Override
            public Response proceed(Request request) throws IOException {
                return new Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .body(ResponseBody.create(MediaType.parse("text/plain; charset=utf-8"), responseBody))
                        .build();
            }
            @Override
            public Connection connection() {
                return null;
            }
        });

    }
}