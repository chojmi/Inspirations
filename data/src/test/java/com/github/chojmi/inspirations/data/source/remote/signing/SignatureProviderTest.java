package com.github.chojmi.inspirations.data.source.remote.signing;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SignatureProviderTest {
    private SignatureProvider signatureProvider;

    @Before
    public void setUp() {
        signatureProvider = new SignatureProvider("SECRET");
    }

    @Test
    public void testProvidingSignatureHappyCase() throws Exception {
        Map<String, String> testArgs = new HashMap<>();
        testArgs.put("foo", "1");
        testArgs.put("bar", "2");
        testArgs.put("baz", "3");

        Map<String, String> signedTestArgs = signatureProvider.provideSigArg(new HashMap<>(testArgs));

        Map<String, String> correctResult = new HashMap<>(testArgs);
        correctResult.put("api_sig", "a626bf097044e8b6f7b9214f049f3cc7");
        assertEquals(correctResult, signedTestArgs);
    }

    @Test
    public void testProvidingSignatureWithDashesHappyCase() throws Exception {
        Map<String, String> testArgs = new HashMap<>();
        testArgs.put("a-b-c", "1");
        testArgs.put("bar", "2");

        Map<String, String> signedTestArgs = signatureProvider.provideSigArg(new HashMap<>(testArgs));

        Map<String, String> correctResult = new HashMap<>(testArgs);
        correctResult.put("api_sig", "a2c90912ec0aa43559cf71d1f2e6c301");
        assertEquals(correctResult, signedTestArgs);
    }
}