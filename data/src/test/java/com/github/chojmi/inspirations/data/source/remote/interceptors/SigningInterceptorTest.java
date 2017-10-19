package com.github.chojmi.inspirations.data.source.remote.interceptors;

import com.github.chojmi.inspirations.data.source.remote.service.OAuthService;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;

@RunWith(MockitoJUnitRunner.class)
public class SigningInterceptorTest {
    private static final String FAKE_URL = "http://www.example.com/";
    private static final String FAKE_SIGNED_GET_URL = "http://www.signed_get_example.com/";
    private static final String FAKE_SIGNED_POST_URL = "http://www.signed_post_example.com/";

    @Mock private OAuthService mockOAuthService;
    @Mock private Interceptor.Chain mockInputChain;

    private SigningInterceptor signingInterceptor;

    @Before
    public void setUp() throws IOException {
        Mockito.when(mockOAuthService.signGetRequest(FAKE_URL)).thenReturn(FAKE_SIGNED_GET_URL);
        Mockito.when(mockOAuthService.signPostRequest(FAKE_URL)).thenReturn(FAKE_SIGNED_POST_URL);

        signingInterceptor = new SigningInterceptor(mockOAuthService);
    }

    @Test
    public void testSigningGetRequest() throws IOException {
        ArgumentCaptor<Request> resultRequest = ArgumentCaptor.forClass(Request.class);
        Request fakeGetRequest = new Request.Builder().url(FAKE_URL).method("GET", null).build();
        Mockito.when(mockInputChain.request()).thenReturn(fakeGetRequest);
        Mockito.when(mockOAuthService.containsAccessToken()).thenReturn(true);

        signingInterceptor.intercept(mockInputChain);

        Mockito.verify(mockOAuthService, Mockito.times(1)).containsAccessToken();
        Mockito.verify(mockOAuthService, Mockito.times(1)).signGetRequest(FAKE_URL);
        Mockito.verify(mockOAuthService, Mockito.never()).signPostRequest(Mockito.any());
        Mockito.verify(mockInputChain, Mockito.times(1)).proceed(resultRequest.capture());
        Assert.assertEquals(FAKE_SIGNED_GET_URL, resultRequest.getValue().url().toString());
        Assert.assertEquals("GET", resultRequest.getValue().method());
    }

    @Test
    public void testSigningPostRequest() throws IOException {
        ArgumentCaptor<Request> resultRequest = ArgumentCaptor.forClass(Request.class);
        Request fakePostRequest = new Request.Builder().url(FAKE_URL).method("POST", Mockito.mock(RequestBody.class)).build();
        Mockito.when(mockInputChain.request()).thenReturn(fakePostRequest);
        Mockito.when(mockOAuthService.containsAccessToken()).thenReturn(true);

        signingInterceptor.intercept(mockInputChain);

        Mockito.verify(mockOAuthService, Mockito.times(1)).containsAccessToken();
        Mockito.verify(mockOAuthService, Mockito.times(1)).signPostRequest(FAKE_URL);
        Mockito.verify(mockOAuthService, Mockito.never()).signGetRequest(Mockito.any());
        Mockito.verify(mockInputChain, Mockito.times(1)).proceed(resultRequest.capture());
        Assert.assertEquals(FAKE_SIGNED_POST_URL, resultRequest.getValue().url().toString());
        Assert.assertEquals("POST", resultRequest.getValue().method());
    }

    @Test
    public void testNotSigningGetRequest() throws IOException {
        ArgumentCaptor<Request> resultRequest = ArgumentCaptor.forClass(Request.class);
        Request fakeGetRequest = new Request.Builder().url(FAKE_URL).method("GET", null).build();
        Mockito.when(mockInputChain.request()).thenReturn(fakeGetRequest);
        Mockito.when(mockOAuthService.containsAccessToken()).thenReturn(false);

        signingInterceptor.intercept(mockInputChain);

        Mockito.verify(mockInputChain, Mockito.times(1)).proceed(resultRequest.capture());
        Mockito.verify(mockOAuthService, Mockito.never()).signPostRequest(Mockito.any());
        Mockito.verify(mockOAuthService, Mockito.never()).signGetRequest(Mockito.any());
        Assert.assertEquals(fakeGetRequest, resultRequest.getValue());
    }

    @Test(expected = IOException.class)
    public void testNotSigningPostRequest() throws IOException {
        Request fakePostRequest = new Request.Builder().url(FAKE_URL).method("POST", Mockito.mock(RequestBody.class)).build();
        Mockito.when(mockInputChain.request()).thenReturn(fakePostRequest);
        Mockito.when(mockOAuthService.containsAccessToken()).thenReturn(false);

        signingInterceptor.intercept(mockInputChain);

        Mockito.verify(mockInputChain, Mockito.never()).proceed(Mockito.any());
        Mockito.verify(mockOAuthService, Mockito.never()).signPostRequest(Mockito.any());
        Mockito.verify(mockOAuthService, Mockito.never()).signGetRequest(Mockito.any());
    }

    @Test
    public void testOtherRequest() throws IOException {
        ArgumentCaptor<Request> resultRequest = ArgumentCaptor.forClass(Request.class);
        Request fakePutRequest = new Request.Builder().url(FAKE_URL).method("PUT", Mockito.mock(RequestBody.class)).build();
        Mockito.when(mockInputChain.request()).thenReturn(fakePutRequest);

        signingInterceptor.intercept(mockInputChain);

        Mockito.verify(mockInputChain, Mockito.times(1)).proceed(resultRequest.capture());
        Mockito.verify(mockOAuthService, Mockito.never()).signPostRequest(Mockito.any());
        Mockito.verify(mockOAuthService, Mockito.never()).signGetRequest(Mockito.any());
        Assert.assertEquals(fakePutRequest, resultRequest.getValue());
    }
}