package com.github.chojmi.inspirations.data.source.utils;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class TestAndroidScheduler extends Scheduler {

    public void init() {
        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> this);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> this);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> this);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> this);
    }

    @Override
    public Worker createWorker() {
        return new ExecutorScheduler.ExecutorWorker(Runnable::run);
    }
}
