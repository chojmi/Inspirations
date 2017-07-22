package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.source.remote.service.OAuthService;
import com.github.chojmi.inspirations.data.source.utils.TestAndroidScheduler;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemoteAuthDataSourceTest {
    @Mock private OAuthService mockOAuthService;

    private RemoteAuthDataSource remoteAuthDataSource;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() {
        this.remoteAuthDataSource = new RemoteAuthDataSource(mockOAuthService);
    }

    @Test
    public void loadAuthorizationUrlHappyCase() throws InterruptedException, ExecutionException, IOException {
        final String correctResult = "correct_result";
        final TestObserver<String> testObserver = new TestObserver<>();
        final OAuth1RequestToken fakeRequestToken = new OAuth1RequestToken("token", "secret_token");
        when(mockOAuthService.getAuthorizationUrl(fakeRequestToken)).thenReturn(correctResult);
        when(mockOAuthService.getRequestToken()).thenReturn(fakeRequestToken);

        remoteAuthDataSource.getAuthorizationUrl(fakeRequestToken).subscribe(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertResult(correctResult);
        testObserver.assertComplete();
    }

    @Test
    public void loadRequestTokenHappyCase() throws InterruptedException, ExecutionException, IOException {
        final OAuth1RequestToken fakeRequestToken = new OAuth1RequestToken("token", "secret_token");
        final TestObserver<OAuth1RequestToken> testObserver = new TestObserver<>();
        when(mockOAuthService.getRequestToken()).thenReturn(fakeRequestToken);

        remoteAuthDataSource.getRequestToken().subscribe(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertResult(fakeRequestToken);
        testObserver.assertComplete();
    }

    @Test
    public void loadAccessTokenHappyCase() throws InterruptedException, ExecutionException, IOException {
        final OAuth1RequestToken fakeRequestToken = new OAuth1RequestToken("request+token", "secret_token");
        final OAuth1AccessToken fakeAccessToken = new OAuth1AccessToken("access_token", "secret_token");
        final String fakeOauthVerifier = "fakeOauthVerifier";
        final TestObserver<OAuth1AccessToken> testObserver = new TestObserver<>();
        when(mockOAuthService.getAccessToken(fakeRequestToken, fakeOauthVerifier)).thenReturn(fakeAccessToken);

        remoteAuthDataSource.getAccessToken(fakeRequestToken, fakeOauthVerifier).subscribe(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertResult(fakeAccessToken);
        testObserver.assertComplete();
    }

    @Test
    public void loadAccessTokenWithoutRequestToken() {
        final String fakeOauthVerifier = "fakeOauthVerifier";
        final TestObserver<OAuth1AccessToken> testObserver = new TestObserver<>();

        remoteAuthDataSource.getAccessToken(fakeOauthVerifier).subscribe(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertError(throwable -> true);
    }

    @Test
    public void loadAuthorizationUrlRequestToken() {
        final TestObserver<String> testObserver = new TestObserver<>();

        remoteAuthDataSource.getAuthorizationUrl().subscribe(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertError(throwable -> true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTryingToSaveAccessToken() {
        final OAuth1AccessToken fakeAccessToken = new OAuth1AccessToken("access_token", "secret_token");

        remoteAuthDataSource.saveAccessToken(fakeAccessToken);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTryingToSaveRequestToken() {
        final OAuth1RequestToken fakeRequestToken = new OAuth1RequestToken("request+token", "secret_token");

        remoteAuthDataSource.saveRequestToken(fakeRequestToken);
    }
}