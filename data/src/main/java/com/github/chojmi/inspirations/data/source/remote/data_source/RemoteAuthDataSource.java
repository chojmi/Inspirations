package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.entity.auth.FrobEntityImpl;
import com.github.chojmi.inspirations.data.entity.auth.TokenEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.service.AuthService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static dagger.internal.Preconditions.checkNotNull;

public class RemoteAuthDataSource {
    private AuthService authService;
    private RemoteQueryProducer remoteQueryProducer;

    public RemoteAuthDataSource(@NonNull AuthService authService, @NonNull RemoteQueryProducer remoteQueryProducer) {
        this.authService = checkNotNull(authService);
        this.remoteQueryProducer = checkNotNull(remoteQueryProducer);
    }

    public Observable<FrobEntityImpl> getFrob() {
        return authService.getFrob(remoteQueryProducer.produceGetFrob())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<TokenEntityImpl> getToken(@NonNull String frob) {
        return authService.getToken(remoteQueryProducer.produceGetToken(checkNotNull(frob)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
