package com.namig.tahmazli.deezerandroidclient;

import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.inject.Inject;

public class BackgroundThreadExecutor implements Executor {
    private final ExecutorService mExecutor;

    @Inject
    public BackgroundThreadExecutor() {

        final ThreadFactory factory = new ThreadFactory() {
            private int threadCount = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format(
                        Locale.getDefault(),
                        "android-thread-%d", ++threadCount));
            }
        };

        mExecutor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                factory
        );
    }

    @Override
    public void execute(Runnable command) {
        mExecutor.execute(command);
    }
}
