package com.github.chojmi.inspirations.domain.common;

import io.reactivex.Flowable;

public interface UseCase<Input, Output> {
    Flowable<SubmitUiModel<Output>> process(Input input);
}
