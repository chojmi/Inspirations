package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public class GetAccessToken implements UseCase<String, OAuth1AccessToken> {

    private final UseCaseProcessor<String, OAuth1AccessToken> processor;

    @Inject
    public GetAccessToken(@NonNull AuthDataSource authDataSource, @NonNull ThreadExecutor threadExecutor,
                          @NonNull PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<String, OAuth1AccessToken>(threadExecutor, postExecutionThread) {
            @Override
            public Flowable<OAuth1AccessToken> getUseCaseActionFlowable(String oauthVerifier) {
                return authDataSource.getAccessToken(oauthVerifier);
            }
        };
    }

    public Flowable<SubmitUiModel<OAuth1AccessToken>> process() {
        return processor.process("");
    }

    @Override
    public Flowable<SubmitUiModel<OAuth1AccessToken>> process(String oauthVerifier) {
        return processor.process(oauthVerifier);
    }
}
