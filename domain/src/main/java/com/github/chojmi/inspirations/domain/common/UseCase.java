package com.github.chojmi.inspirations.domain.common;

import io.reactivex.Observable;

public interface UseCase<Input, Output> {
    Observable<SubmitUiModel<Output>> process(Input input);
}
