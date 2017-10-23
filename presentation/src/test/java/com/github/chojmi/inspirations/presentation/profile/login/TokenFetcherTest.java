package com.github.chojmi.inspirations.presentation.profile.login;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TokenFetcherTest {
    @Test
    public void validProcessHTMLHappyCase() {
        TokenFetcher tokenFetcher = new TokenFetcher(token -> assertEquals("122-111-111", token));
        tokenFetcher.processHTML("asasa>122-111-111<asasas");
    }

    @Test
    public void invalidProcessHTMLHappyCase() {
        TokenFetcher tokenFetcher = new TokenFetcher(token -> assertEquals("", token));
        tokenFetcher.processHTML("asasa>122-11-11<asasas");
    }
}